package com.rayllanderson.raybank.card.events;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import com.rayllanderson.raybank.event.Event;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CardPaymentCompletedEvent implements Event {

    private final String cardId;
    private final BigDecimal total;
    private final String description;
    private final String establishmentId;
    private final String paymentType;
    private final Integer installments;
    private final String transactionId;
    private final LocalDateTime ocurredOn;

    public CardPaymentCompletedEvent(final CardPaymentTransaction payment) {
        this.cardId = payment.getDebit().getId();
        this.total = payment.getAmount();
        this.description = payment.getDescription();
        this.establishmentId = payment.getCredit().getId();
        this.ocurredOn = payment.getMoment();
        this.transactionId = payment.getId();

        if (payment.isCreditTransaction()) {
            this.paymentType = PaymentCardInput.PaymentType.CREDIT.name();
            this.installments = payment.getInstallments();
        } else {
            this.paymentType = PaymentCardInput.PaymentType.DEBIT.name();
            this.installments = null;
        }
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }
}
