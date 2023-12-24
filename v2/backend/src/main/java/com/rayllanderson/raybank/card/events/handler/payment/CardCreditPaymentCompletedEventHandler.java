package com.rayllanderson.raybank.card.events.handler.payment;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.card.events.handler.payment.services.CardPaymentHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class CardCreditPaymentCompletedEventHandler {

    private final List<CardPaymentHandlerService> services;

    @Async
    @TransactionalEventListener
    public void handler(final CardCreditPaymentCompletedEvent event) {
        final String cardId = event.getCardId();
        log.info("Handling CardPaymentEvent for card id {}", cardId);

        services.forEach(service -> service.process(event));
    }
}
