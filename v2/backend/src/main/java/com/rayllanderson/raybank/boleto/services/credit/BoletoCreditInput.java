package com.rayllanderson.raybank.boleto.services.credit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BoletoCreditInput {
    private String beneficiaryId;
    private String beneficiaryType;
    private String barCode;
    private BigDecimal amount;
    private String originalTransactionId;
}
