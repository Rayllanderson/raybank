package com.rayllanderson.raybank.card.models.inputs;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class DebitCardPayment extends CardPayment {
    public DebitCardPayment(BigDecimal total, LocalDateTime ocurredOn, String description) {
        super(total, ocurredOn, description);
    }
}
