package com.rayllanderson.raybank.dtos.requests.bank;

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
