package com.rayllanderson.raybank.card.events;

import com.rayllanderson.raybank.card.models.inputs.CardPayment;
import com.rayllanderson.raybank.card.models.inputs.CreditCardPayment;
import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import com.rayllanderson.raybank.event.Event;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CardPaymentEvent implements Event {

    private final String cardId;
    private final BigDecimal total;
    private final String description;
    private final String establishmentId;
    private final String paymentType;
    private final Integer installments;
    private final LocalDateTime ocurredOn;

    public CardPaymentEvent(CardPayment payment, String cardId) {
        this.cardId = cardId;
        this.total = payment.getTotal();
        this.description = payment.getDescription();
        this.establishmentId = payment.getEstablishmentId();
        this.ocurredOn = payment.getOcurredOn();

        if (payment instanceof CreditCardPayment) {
            this.paymentType = PaymentCardInput.PaymentType.CREDIT.name();
            this.installments = ((CreditCardPayment) payment).getInstallments();
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
