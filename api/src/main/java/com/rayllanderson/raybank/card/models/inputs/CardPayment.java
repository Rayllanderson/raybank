package com.rayllanderson.raybank.card.models.inputs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public abstract class CardPayment {
    final BigDecimal total;
    final LocalDateTime ocurredOn;
    final String description;
}
