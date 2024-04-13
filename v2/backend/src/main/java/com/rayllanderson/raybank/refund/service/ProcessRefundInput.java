package com.rayllanderson.raybank.refund.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class ProcessRefundInput {
    private final String transactionId;
    private final BigDecimal amount;
    private final String reason;
}
