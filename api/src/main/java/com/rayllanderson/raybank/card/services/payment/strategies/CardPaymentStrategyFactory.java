package com.rayllanderson.raybank.card.services.payment.strategies;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardPaymentStrategyFactory {

    private final List<CardPaymentStrategy> strategies;

    public CardPaymentStrategy getStrategyBy(PaymentCardInput.PaymentType paymentType) {
        return strategies
                .stream()
                .filter(strategy -> strategy.supports(paymentType))
                .findFirst()
                .orElseThrow(CardPaymentStrategyNotFoundException::new);
    }
}
