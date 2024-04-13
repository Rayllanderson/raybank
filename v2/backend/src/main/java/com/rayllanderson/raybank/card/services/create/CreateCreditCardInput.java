package com.rayllanderson.raybank.card.services.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreateCreditCardInput {
    private final String accountId;
    private final BigDecimal limit;
    private final DueDays dueDay;
}
