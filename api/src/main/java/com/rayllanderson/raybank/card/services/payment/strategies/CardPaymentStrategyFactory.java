package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardPaymentStrategyFactory {

    private final List<CardPaymentStrategy> strategies;

    public CardPaymentStrategy getStrategyBy(CardPaymentInput.PaymentType paymentType) {
        return strategies
                .stream()
                .filter(strategy -> strategy.supports(paymentType))
                .findFirst()
                .orElseThrow(CardPaymentStrategyNotFoundException::new);
    }

    public static class CardPaymentStrategyNotFoundException extends RuntimeException {
        public CardPaymentStrategyNotFoundException() {
            super("No strategies were found for card payment");
        }
    }
}
