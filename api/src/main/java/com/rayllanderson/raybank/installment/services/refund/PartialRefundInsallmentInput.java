package com.rayllanderson.raybank.installment.services.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PartialRefundInsallmentInput {
    private final String planId;
    private final BigDecimal amount;
}
