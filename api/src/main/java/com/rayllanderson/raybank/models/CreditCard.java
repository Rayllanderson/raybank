package com.rayllanderson.raybank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.models.inputs.CardPayment;
import com.rayllanderson.raybank.models.inputs.CreditCardPayment;
import com.rayllanderson.raybank.models.inputs.DebitCardPayment;
import com.rayllanderson.raybank.models.statements.BankStatement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;
import static com.rayllanderson.raybank.utils.InstallmentUtil.calculateInstallmentValue;
import static com.rayllanderson.raybank.utils.InstallmentUtil.createDescription;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditCard {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    private Long number;
    private Integer securityCode;
    private YearMonth expiryDate;
    @Column(name = "a_limit")
    private BigDecimal limit;
    private BigDecimal balance;
    private Integer dayOfDueDate;
    @JsonIgnore
    @OneToOne
    private BankAccount bankAccount;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Invoice> invoices;

    public static CreditCard create(Long number, BigDecimal limit, Integer securityCode, YearMonth expiryDate, Integer dueDate, BankAccount bankAccount) {
        final var c = CreditCard.builder()
                .id(UUID.randomUUID().toString())
                .number(number)
                .bankAccount(bankAccount)
                .limit(limit)
                .balance(limit)
                .securityCode(securityCode)
                .expiryDate(expiryDate)
                .dayOfDueDate(dueDate)
                .invoices(new HashSet<>())
                .build();
        c.invoices.add(Invoice.createOpenInvoice(plusOneMonthOf(dueDate)));
        return c;
    }

    public BankStatement payCurrentInvoice(final BigDecimal amount) {
        final var currentInvoice = getCurrentInvoiceToPay();

        return payInvoice(currentInvoice, amount);
    }

    public BankStatement payInvoiceById(final String invoiceId, final BigDecimal amount) {
        final Invoice invoice = getInvoiceById(invoiceId).orElseThrow(() -> new NotFoundException("Fatura não encontrada"));

        return payInvoice(invoice, amount);
    }

    public BankStatement payInvoice(Invoice invoice, BigDecimal amount) {
        if (!invoice.hasValueToPay()) {
            throw new UnprocessableEntityException("Fatura não possui nenhum valor em aberto");
        }

        if (!this.bankAccount.hasAvailableBalance(amount)) {
            throw new BadRequestException("Sua conta não possui saldo suficiente para pagar a fatura.");
        }

        invoice.receivePayment(amount);

        balance = balance.add(amount);
        bankAccount.pay(amount);

        return createInvoiceBankStatement(amount);
    }

    private Optional<Invoice> getInvoiceById(final String invoiceId) {
        return this.invoices.stream().filter(invoice -> invoice.getId().equals(invoiceId)).findFirst();
    }

    public void pay(final CardPayment payment) throws UnprocessableEntityException {
        if (payment instanceof CreditCardPayment) {
            this.pay((CreditCardPayment) payment);
            return;
        }
        this.pay((DebitCardPayment) payment);
    }

    public void pay(final CreditCardPayment payment) throws UnprocessableEntityException {
        if (this.hasLimit()) {
            if (isAmountGreaterThanBalance(payment.getTotal())) {
                throw new UnprocessableEntityException("Falha na transação. O valor da compra é maior que seu saldo disponível no cartão.");
            }

            if (this.isExpired())
                throw UnprocessableEntityException.with("Cartão está expirado");

            processInvoice(payment.getTotal(), payment.getInstallments(), payment.getDescription(), payment.getOcurredOn());

            balance = balance.subtract(payment.getTotal());
//            this.createPurchaseBankStatement(payment.getTotal(), payment.getDescription());
        } else
            throw new UnprocessableEntityException("Seu cartão não possui saldo suficiente para esta compra.");
    }

    public void pay(final DebitCardPayment payment) throws UnprocessableEntityException {
        if (this.isExpired())
            throw UnprocessableEntityException.with("Cartão está expirado");
        try {
            this.bankAccount.pay(payment.getTotal());
            this.createDebitBankStatement(payment.getTotal(), payment.getDescription());
        } catch (UnprocessableEntityException e) {
            throw new UnprocessableEntityException("Saldo em conta insuficiente para efetuar compra no débito");
        }
    }

    private BankStatement createInvoiceBankStatement(BigDecimal amount) {
        var bankStatement = BankStatement.createInvoicePaymentBankStatement(amount, bankAccount);
        return bankStatement;
    }

    private BankStatement createDebitBankStatement(BigDecimal amount, String message) {
        var bankStatement = BankStatement.createDebitCardBankStatement(amount, bankAccount, message);
        return bankStatement;
    }

    private BankStatement createPurchaseBankStatement(BigDecimal amount, String message) {
        var bankStatement = BankStatement.createCreditBankStatement(amount, bankAccount, message);
        return bankStatement;
    }

    public boolean isValidSecurityCode(final Integer securityCode) {
        return Objects.equals(this.securityCode, securityCode);
    }

    public boolean isValidExpiryDate(final YearMonth expiryDate) {
        return Objects.equals(this.expiryDate, expiryDate);
    }

    public boolean isAmountGreaterThanBalance(BigDecimal amount) {
        return amount.compareTo(balance) > 0;
    }

    public boolean hasLimit() {
        return !(balance.equals(BigDecimal.ZERO) || balance.equals(new BigDecimal("0.00")));
    }

    protected void processInvoice(BigDecimal total, int installments, String paymentDescription, LocalDateTime ocurredOn) {
        final Invoice currentInvoice = getCurrentOpenInvoice();
        checkOcurredDateItsOnRange(currentInvoice, ocurredOn.toLocalDate());

        final var installmentValue = calculateInstallmentValue(total, installments);
        currentInvoice.processPayment(createDescription(paymentDescription, 1, installments), total, installmentValue, ocurredOn);

        this.invoices.add(currentInvoice);

        Invoice invoiceCopy = currentInvoice;

        for (int i = 1; i < installments; i++) {
            final var nextInvoice = getNextOf(invoiceCopy);
            invoiceCopy = nextInvoice.orElse(Invoice.create(getNextInvoiceDate(invoiceCopy)));
            invoiceCopy.processPayment(createDescription(paymentDescription, i + 1, installments), total, installmentValue, ocurredOn);
            this.invoices.add(invoiceCopy);
        }
    }

    public Invoice getCurrentOpenInvoice() {
        return getInvoiceBeforeClosingDateBy(LocalDate.now())
                .orElse(Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate)));
    }

    public Invoice getCurrentClosedInvoice() {
        return getInvoiceBeforeOrEqualsDueDateBy(LocalDate.now()).orElse(null);
    }

    private Invoice getCurrentInvoiceToPay() {
        var currentClosed = getCurrentClosedInvoice();
        if (currentClosed != null && !currentClosed.isPaid())
            return currentClosed;
        return getCurrentOpenInvoice();
    }

    private static void checkOcurredDateItsOnRange(Invoice currentInvoice, LocalDate ocurredOn) {
        if (ocurredOn.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }

        final LocalDate oldClosingDate = currentInvoice.getClosingDate().minusMonths(1);
        if (isAfterOrEquals(oldClosingDate, ocurredOn)) {
            throw new IllegalArgumentException("'ocurredOn' must not be before current invoice closing date");
        }
    }

    private LocalDate getNextInvoiceDate(Invoice invoiceCopy) {
        final Month month = invoiceCopy.getDueDate().getMonth();
        final var year = invoiceCopy.getDueDate().getYear();
        return plusOneMonthKeepingCurrentDayOfMonth(LocalDate.of(year, month, dayOfDueDate));
    }

    private Optional<Invoice> getInvoiceBeforeClosingDateBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final ArrayList<Invoice> sortedInvoices = getSortedInvoices();
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getClosingDate()))
                .findFirst();
    }

    private Optional<Invoice> getInvoiceBeforeOrEqualsDueDateBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final ArrayList<Invoice> sortedInvoices = getSortedInvoices();
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getDueDate()) || date.isEqual(invoice.getDueDate()))
                .findFirst();
    }

    private ArrayList<Invoice> getSortedInvoices() {
        final var sortedInvoices = new ArrayList<>(invoices);
        Collections.sort(sortedInvoices);
        return sortedInvoices;
    }

    private Optional<Invoice> getNextOf(final Invoice invoice) {
        return getInvoiceBeforeClosingDateBy(invoice.getDueDate().plusDays(1));
    }

    /**
     * @return unmodifiableSet
     */
    public Set<Invoice> getInvoices() {
        return Collections.unmodifiableSet(invoices);
    }

    public boolean isExpired() {
        return YearMonth.now().isAfter(this.expiryDate);
    }

    public Long getAccountId() { //todo::getBankAccountId
        return this.getBankAccount().getId();
    }
}
