package com.rayllanderson.raybank.boleto.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class GenerateBoletoResponse {
    private final String barCode;
    private final BigDecimal value;
    private final LocalDate creationAt;
    private final LocalDate expirationDate;
}
