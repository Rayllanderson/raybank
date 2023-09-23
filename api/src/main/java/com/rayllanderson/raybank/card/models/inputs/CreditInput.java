package com.rayllanderson.raybank.card.models.inputs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreditInput {

    private final BigDecimal amount;
    private final CreditOrigin origin;

    @Getter
    @RequiredArgsConstructor
    public static class CreditOrigin {
        private final String identifier;
        private final CreditOriginType type;
    }
}
