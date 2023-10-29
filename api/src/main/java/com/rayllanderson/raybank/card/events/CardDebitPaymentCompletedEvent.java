package com.rayllanderson.raybank.card.events;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.shared.event.Event;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CardDebitPaymentCompletedEvent implements Event {

    private final String cardId;
    private final BigDecimal total;
    private final String description;
    private final String establishmentId;
    private final String paymentType;
    private final String transactionId;
    private final LocalDateTime ocurredOn;

    public CardDebitPaymentCompletedEvent(final Transaction payment) {
        this.cardId = payment.getDebit().getId();
        this.total = payment.getAmount();
        this.description = payment.getDescription();
        this.establishmentId = payment.getCredit().getId();
        this.ocurredOn = payment.getMoment();
        this.transactionId = payment.getId();
        this.paymentType = PaymentCardInput.PaymentType.DEBIT.name();
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }
}
