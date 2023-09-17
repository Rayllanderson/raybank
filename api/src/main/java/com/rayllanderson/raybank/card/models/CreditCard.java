package com.rayllanderson.raybank.card.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.card.events.CardPaymentEvent;
import com.rayllanderson.raybank.card.events.CreditCardCreatedEvent;
import com.rayllanderson.raybank.card.models.inputs.CardPayment;
import com.rayllanderson.raybank.card.models.inputs.CreditCardPayment;
import com.rayllanderson.raybank.card.models.inputs.DebitCardPayment;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.statement.models.BankStatement;
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
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CreditCard extends AbstractAggregateRoot<CreditCard> {

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
    private CardStatus status;
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
                .status(CardStatus.ANALYSIS)
                .build();
        c.registerEvent(new CreditCardCreatedEvent(c.id));
        return c;
    }

    public static CreditCard withId(final String cardId) {
        return CreditCard.builder().id(cardId).build();
    }

    public BankStatement payCurrentInvoice(final BigDecimal amount) {
//        final var currentInvoice = getCurrentInvoiceToPay();
return null;
//        return payInvoice(currentInvoice, amount);
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

        balance = balance.add(amount); //por event, maybe
        bankAccount.pay(amount);

        return createInvoiceBankStatement(amount);
    }

    private Optional<Invoice> getInvoiceById(final String invoiceId) {
        return this.invoices.stream().filter(invoice -> invoice.getId().equals(invoiceId)).findFirst();
    }

    public void pay(final CardPayment payment) throws UnprocessableEntityException {
        if (!isActive()) {
            throw new UnprocessableEntityException("Cartão não está ativo para compras");
        }
        if (payment instanceof CreditCardPayment)
            this.pay((CreditCardPayment) payment);

        if (payment instanceof DebitCardPayment)
            this.pay((DebitCardPayment) payment);

        registerEvent(new CardPaymentEvent(payment, this.id));
    }

    protected void pay(final CreditCardPayment payment) throws UnprocessableEntityException {
        if (this.hasLimit()) {
            if (isAmountGreaterThanBalance(payment.getTotal())) {
                throw new UnprocessableEntityException("Falha na transação. O valor da compra é maior que seu saldo disponível no cartão.");
            }

            if (this.isExpired())
                throw UnprocessableEntityException.with("Cartão está expirado");

            balance = balance.subtract(payment.getTotal());
//            this.createPurchaseBankStatement(payment.getTotal(), payment.getDescription());
        } else
            throw new UnprocessableEntityException("Seu cartão não possui saldo suficiente para esta compra.");
    }

    protected void pay(final DebitCardPayment payment) throws UnprocessableEntityException {
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

    /**
     * @return unmodifiableSet
     */
    public Set<Invoice> getInvoices() {
        return Collections.unmodifiableSet(invoices);
    }

    public boolean isExpired() {
        return YearMonth.now().isAfter(this.expiryDate);
    }

    public String getAccountId() { //todo::getBankAccountId
        return this.getBankAccount().getId();
    }

    public void activate() {
        this.status = CardStatus.ACTIVE;
    }

    public boolean isActive() {
        return CardStatus.ACTIVE.equals(status);
    }
}
