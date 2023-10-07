package com.rayllanderson.raybank.refund.service.strategies;

public class RefundStrategyNotFoundException extends RuntimeException {
    public RefundStrategyNotFoundException() {
        super("No strategies were found for refund");
    }
}
