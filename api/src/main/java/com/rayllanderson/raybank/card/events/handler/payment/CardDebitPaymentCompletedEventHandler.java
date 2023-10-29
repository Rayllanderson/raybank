package com.rayllanderson.raybank.card.events.handler.payment;

import com.rayllanderson.raybank.card.events.CardDebitPaymentCompletedEvent;
import com.rayllanderson.raybank.card.events.handler.payment.services.CreditEstablishmentHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class CardDebitPaymentCompletedEventHandler {

    private final CreditEstablishmentHandlerService service;

    @Async
    @TransactionalEventListener
    public void handler(final CardDebitPaymentCompletedEvent event) {
        final String cardId = event.getCardId();
        log.info("Handling CardDebitPaymentEvent for card id {}", cardId);

        service.process(event);
    }
}
