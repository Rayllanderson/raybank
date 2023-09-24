package com.rayllanderson.raybank.card.events.handler;

import com.rayllanderson.raybank.card.events.CardPaymentEvent;
import com.rayllanderson.raybank.invoice.services.ProcessInvoiceService;
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
public class CardPaymentEventHandler {

    private final ProcessInvoiceService processInvoiceService;

    @Async
    @TransactionalEventListener
    public void handler(final CardPaymentEvent event) {
        final String cardId = event.getCardId();
        log.info("Handling CardPaymentEvent for card id {}", cardId);

        processInvoiceService.processInvoice(event.getTotal(), event.getInstallments(), event.getDescription(), event.ocurredOn(), event.getCardId());
    }
}
