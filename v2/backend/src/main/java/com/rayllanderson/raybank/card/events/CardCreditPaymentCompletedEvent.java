package com.rayllanderson.raybank.card.events;

import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.shared.event.Event;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CardCreditPaymentCompletedEvent implements Event {

    private final String cardId;
    private final BigDecimal total;
    private final String description;
    private final String establishmentId;
    private final String paymentType;
    private final Integer installments;
    private final String transactionId;
    private final String planId;
    private final LocalDateTime ocurredOn;

    public CardCreditPaymentCompletedEvent(final CardCreditPaymentTransaction payment) {
        this.cardId = payment.getDebit().getId();
        this.total = payment.getAmount();
        this.description = payment.getDescription();
        this.establishmentId = payment.getCredit().getId();
        this.ocurredOn = payment.getMoment();
        this.transactionId = payment.getId();
        this.paymentType = CardPaymentInput.PaymentType.CREDIT.name();
        this.installments = payment.getInstallments();
        this.planId = payment.getPlanId();
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }
}
