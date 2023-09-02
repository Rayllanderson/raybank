package com.rayllanderson.raybank.services.inputs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreateCreditCardInput {
    private final Long bankAccountId;
    private final BigDecimal limit;
    private final Integer dueDay;
}
