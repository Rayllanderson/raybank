package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.refund.controller.RefundRequest;

import java.math.BigDecimal;

public class RefundRequestBuilder {

    public static RefundRequest build(BigDecimal amount, String reason) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setAmount(amount);
        refundRequest.setReason(reason);
        return refundRequest;
    }
}
