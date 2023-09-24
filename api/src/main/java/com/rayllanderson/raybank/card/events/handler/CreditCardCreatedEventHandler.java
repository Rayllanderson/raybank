package com.rayllanderson.raybank.card.events.handler;

import com.rayllanderson.raybank.card.events.CreditCardCreatedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class CreditCardCreatedEventHandler {

    private final CardRepository cardRepository;
    private final InvoiceRepository invoiceRepository;

    @Async
    @TransactionalEventListener
    public void handler(final CreditCardCreatedEvent event) {
        log.info("Handling CreditCardCreatedEvent={}", event.id());

        final Card card = cardRepository.findById(event.id())
                .orElseThrow(() -> new NotFoundException(String.format("Card %s was not found", event.id())));

        final var firstInvoice = Invoice.createOpenInvoice(plusOneMonthOf(card.getDayOfDueDate()), card.getId());
        invoiceRepository.save(firstInvoice);

        card.activate();
        cardRepository.save(card);
    }
}
