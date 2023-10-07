package com.rayllanderson.raybank.refund.controller;

import com.rayllanderson.raybank.refund.service.RefundOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class RefundResponse {
    private final String transactionId;
    private final BigDecimal amount;
    private final String reason;

    public static RefundResponse fromOutput(final RefundOutput o, final String reason) {
        return new RefundResponse(o.getTransactionId(), o.getAmount(), reason);
    }
}
