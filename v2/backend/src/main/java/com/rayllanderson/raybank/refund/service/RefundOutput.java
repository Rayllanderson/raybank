package com.rayllanderson.raybank.refund.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class RefundOutput {
    private final String transactionId;
    private final BigDecimal amount;
}
