package com.rayllanderson.raybank.card.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.events.CreditCardCreatedEvent;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
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
public class Card extends AbstractAggregateRoot<Card> {

    @EqualsAndHashCode.Include
    @Id
    private String id;
    private Long number;
    private Integer securityCode;
    private YearMonth expiryDate;
    @Column(name = "a_limit")
    private BigDecimal limit;
    private Integer dayOfDueDate;
    private CardStatus status;
    @JsonIgnore
    @OneToOne
    private BankAccount bankAccount;

    public static Card create(Long number, BigDecimal limit, Integer securityCode, YearMonth expiryDate, Integer dueDate, BankAccount bankAccount) {
        final var c = Card.builder()
                .id(UUID.randomUUID().toString())
                .number(number)
                .bankAccount(bankAccount)
                .limit(limit)
                .securityCode(securityCode)
                .expiryDate(expiryDate)
                .dayOfDueDate(dueDate)
                .status(CardStatus.ANALYSIS)
                .build();
        c.registerEvent(new CreditCardCreatedEvent(c.id));
        return c;
    }

    public static Card withId(final String cardId) {
        return Card.builder().id(cardId).build();
    }

    public boolean isValidSecurityCode(final Integer securityCode) {
        return Objects.equals(this.securityCode, securityCode);
    }

    public boolean isValidExpiryDate(final YearMonth expiryDate) {
        return Objects.equals(this.expiryDate, expiryDate);
    }

    public boolean isExpired() {
        return YearMonth.now().isAfter(this.expiryDate);
    }

    public String getAccountId() {
        return this.getBankAccount().getId();
    }

    public void activate() {
        this.status = CardStatus.ACTIVE;
    }

    public boolean isActive() {
        return CardStatus.ACTIVE.equals(status);
    }

    public void changeLimit(final BigDecimal newLimit) {
        this.limit = newLimit;
    }
}
