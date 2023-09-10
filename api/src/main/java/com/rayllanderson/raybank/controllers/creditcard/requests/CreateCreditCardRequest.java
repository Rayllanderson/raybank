package com.rayllanderson.raybank.controllers.creditcard.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateCreditCardRequest {
    private final BigDecimal limit;
    private final Integer dueDay;
}
