package com.rayllanderson.raybank.invoice.events.handler;

import com.rayllanderson.raybank.card.facades.CardCreditFacade;
import com.rayllanderson.raybank.card.facades.CardCreditFacadeInput;
import com.rayllanderson.raybank.invoice.events.InvoicePaidEvent;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
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
public class InvoicePaidEventHandler {

    private final InvoiceGateway invoiceGateway;
    private final CardCreditFacade cardCreditFacade;

    @Async
    @TransactionalEventListener
    public void handler(final InvoicePaidEvent event) {
        final String invoiceId = event.getInvoiceId();
        log.info("Handling InvoicePaidEvent for invoice id {}", invoiceId);

        final Invoice invoicePaid = invoiceGateway.findById(invoiceId);

        final var credit = CardCreditFacadeInput.createFromInvoicePayment(event.getAmount(), invoicePaid.getCardId(), invoiceId, event.getTransactionId());
        cardCreditFacade.process(credit);
    }
}
