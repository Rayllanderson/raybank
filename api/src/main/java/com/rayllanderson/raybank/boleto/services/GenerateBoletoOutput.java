package com.rayllanderson.raybank.boleto.services;

import com.rayllanderson.raybank.boleto.models.Boleto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class GenerateBoletoOutput {
    private final String barCode;
    private final BigDecimal value;
    private final LocalDate creationAt;
    private final LocalDate expirationDate;

    public static GenerateBoletoOutput from(final Boleto boleto) {
        return new GenerateBoletoOutput(boleto.getBarCode(), boleto.getValue(), boleto.getCreatedAt(), boleto.getExpirationDate());
    }
}
