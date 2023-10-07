package com.rayllanderson.raybank.installment.services.refund;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FullRefundInsallmentInput {
    private String planId;
    private BigDecimal amount;
}
