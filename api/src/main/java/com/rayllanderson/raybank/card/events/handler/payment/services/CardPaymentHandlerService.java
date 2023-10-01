package com.rayllanderson.raybank.card.events.handler.payment.services;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;

public interface CardPaymentHandlerService {
    void process(CardCreditPaymentCompletedEvent event);
}
