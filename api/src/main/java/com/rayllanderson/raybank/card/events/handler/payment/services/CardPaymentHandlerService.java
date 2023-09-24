package com.rayllanderson.raybank.card.events.handler.payment.services;

import com.rayllanderson.raybank.card.events.CardPaymentCompletedEvent;

public interface CardPaymentHandlerService {
    void process(CardPaymentCompletedEvent event);
}
