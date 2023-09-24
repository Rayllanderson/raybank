package com.rayllanderson.raybank.card.services.payment.strategies;

public class CardPaymentStrategyNotFoundException extends RuntimeException {
    public CardPaymentStrategyNotFoundException() {
        super("No strategies were found for card payment");
    }
}
