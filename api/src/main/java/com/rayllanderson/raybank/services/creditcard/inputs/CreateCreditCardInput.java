package com.rayllanderson.raybank.services.creditcard.inputs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreateCreditCardInput {
    private final String userId;
    private final BigDecimal limit;
    private final DueDays dueDay;
}
