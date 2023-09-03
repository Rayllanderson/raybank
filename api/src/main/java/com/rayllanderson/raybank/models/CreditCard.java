package com.rayllanderson.raybank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.models.inputs.CreditCardPayment;
import com.rayllanderson.raybank.models.inputs.DebitCardPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Invoice> invoices;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Transaction> transactions = new ArrayList<>();

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
        c.invoices.add(Invoice.create(plusOneMonthOf(dueDate)));
        return c;
    }

    public void payTheInvoice(BigDecimal amount) throws IllegalArgumentException, BadRequestException {
        if (this.hasInvoice()) {
            if (this.bankAccount.hasAvailableBalance(amount)) {
                final var currentInvoice = getCurrentInvoice();
                currentInvoice.receivePayment(amount);
                balance = balance.add(amount);
                bankAccount.pay(amount);
                createInvoiceTransaction(amount);
            } else {
                throw new BadRequestException("Sua conta não possui saldo suficiente para pagar a fatura.");
            }
        } else {
            throw new BadRequestException("Ops, parece que seu cartão não possui nenhuma fatura.");
        }
    }

    public Transaction pay(final CreditCardPayment payment) throws UnprocessableEntityException {
        if (this.hasLimit()) {
            if (isAmountGreaterThanBalance(payment.getTotal())) {
                throw new UnprocessableEntityException("Falha na transação. O valor da compra é maior que seu saldo disponível no cartão.");
            }

            processInvoice(payment.getTotal(), payment.getInstallments(), payment.getDescription(), payment.getOcurredOn());

            balance = balance.subtract(payment.getTotal());
            return this.createPurchaseTransaction(payment.getTotal());
        } else
            throw new UnprocessableEntityException("Seu cartão não possui saldo suficiente para esta compra.");
    }

    public Transaction pay(final DebitCardPayment payment) throws UnprocessableEntityException {
        try {
            this.bankAccount.pay(payment.getTotal());
            return this.createDebitTransaction(payment.getTotal());
        } catch (UnprocessableEntityException e) {
            throw new UnprocessableEntityException("Saldo em conta insuficiente para efetuar compra no débito");
        }
    }

    private void createInvoiceTransaction(BigDecimal amount) {
        var transaction = Transaction.createInvoicePaymentTransaction(amount, bankAccount);
        this.getTransactions().add(transaction);
    }

    private Transaction createDebitTransaction(BigDecimal amount) {
        var transaction = Transaction.createDebitCardTransaction(amount, bankAccount);
        this.getTransactions().add(transaction);
        return transaction;
    }

    private Transaction createPurchaseTransaction(BigDecimal amount) {
        var transaction = Transaction.createCreditTransaction(amount, bankAccount);
        this.getTransactions().add(transaction);
        return transaction;
    }

    public void payInvoiceAndRefundRemaining(BigDecimal amount) {
        BigDecimal refund = amount.subtract(getCurrentInvoice().getTotal());
        this.payTheInvoice(amount.subtract(refund));
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

    public boolean hasInvoice() {
        return !getCurrentInvoice().hasValueToPay();
    }

    protected void processInvoice(BigDecimal total, int installments, String paymentDescription, LocalDateTime ocurredOn) {
        final Invoice currentInvoice = getCurrentInvoice();
        checkOcurredDateItsOnRange(currentInvoice, ocurredOn.toLocalDate());

        final var installmentValue = calculateInstallmentValue(total, installments);
        currentInvoice.processPayment(createDescription(paymentDescription, 1, installments), total, installmentValue, ocurredOn);

        Invoice invoiceCopy = currentInvoice;

        for (int i = 1; i < installments; i++) {
            final var nextInvoice = getNextOf(invoiceCopy);
            invoiceCopy = nextInvoice.orElse(Invoice.create(getNextInvoiceDate(invoiceCopy)));
            invoiceCopy.processPayment(createDescription(paymentDescription, i + 1, installments), total, installmentValue, ocurredOn);
            this.invoices.add(invoiceCopy);
        }
    }

    public Invoice getCurrentInvoice() {
        return getInvoiceBy(LocalDate.now())
                .orElseThrow(() -> new IllegalStateException("No currency invoice was found"));
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

    private Optional<Invoice> getInvoiceBy(final LocalDate date) {
        if (date == null) throw new NullPointerException("'date' must not be null");
        final var sortedInvoices = new ArrayList<>(invoices);
        Collections.sort(sortedInvoices);
        return sortedInvoices.stream()
                .filter(invoice -> date.isBefore(invoice.getClosingDate()))
                .findFirst();
    }

    private Optional<Invoice> getNextOf(final Invoice invoice) {
        return getInvoiceBy(invoice.getDueDate().plusDays(1));
    }

    /**
     * @return unmodifiableSet
     */
    public Set<Invoice> getInvoices() {
        return Collections.unmodifiableSet(invoices);
    }
}
