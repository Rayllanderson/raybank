package com.rayllanderson.raybank.card.models.inputs;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CreditCardPayment extends CardPayment {
    private final int installments;

    public CreditCardPayment(BigDecimal total, LocalDateTime ocurredOn, int installments, String description, String establishmentId) {
        super(total, ocurredOn, description, establishmentId);
        this.installments = installments;
    }
}
