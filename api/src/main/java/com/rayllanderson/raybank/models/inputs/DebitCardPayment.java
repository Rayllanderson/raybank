package com.rayllanderson.raybank.models.inputs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class DebitCardPayment extends CardPayment {
    public DebitCardPayment(BigDecimal total, LocalDateTime ocurredOn, String description) {
        super(total, ocurredOn, description);
    }
}
