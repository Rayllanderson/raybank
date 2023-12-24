package com.rayllanderson.raybank.installment.services.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class FullRefundInsallmentOutput {
    private final String planId;
    private final BigDecimal amountRefunded;
}
