package com.rayllanderson.raybank.card.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.events.CardPaymentEvent;
import com.rayllanderson.raybank.card.events.CreditCardCreatedEvent;
import com.rayllanderson.raybank.card.models.inputs.CardPayment;
import com.rayllanderson.raybank.card.models.inputs.CreditCardPayment;
import com.rayllanderson.raybank.card.models.inputs.DebitCardPayment;
import com.rayllanderson.raybank.card.models.inputs.CreditInput;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;
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
                .status(CardStatus.ANALYSIS)
                .build();
        c.registerEvent(new CreditCardCreatedEvent(c.id));
        return c;
    }

    public static CreditCard withId(final String cardId) {
        return CreditCard.builder().id(cardId).build();
    }

    public void credit(final CreditInput creditInput) {
        this.balance = balance.add(creditInput.getAmount());
        //todo:: evento
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
        } else
            throw new UnprocessableEntityException("Seu cartão não possui saldo suficiente para esta compra.");
    }

    protected void pay(final DebitCardPayment payment) throws UnprocessableEntityException {
        if (this.isExpired())
            throw UnprocessableEntityException.with("Cartão está expirado");
        try {
            this.bankAccount.pay(payment.getTotal());
        } catch (UnprocessableEntityException e) {
            throw new UnprocessableEntityException("Saldo em conta insuficiente para efetuar compra no débito");
        }
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
