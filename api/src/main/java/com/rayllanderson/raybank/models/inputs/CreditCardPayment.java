package com.rayllanderson.raybank.models.inputs;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CreditCardPayment extends CardPayment {
    final int installments;

    public CreditCardPayment(BigDecimal total, LocalDateTime ocurredOn, int installments, String description) {
        super(total, ocurredOn, description);
        this.installments = installments;
    }
}
