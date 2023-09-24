package com.rayllanderson.raybank.card.events.handler.payment.services;

import com.rayllanderson.raybank.card.events.CardPaymentCompletedEvent;
import com.rayllanderson.raybank.invoice.services.ProcessInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessInvoiceHandlerService implements CardPaymentHandlerService {

    private final ProcessInvoiceService processInvoiceService;

    @Override
    public void process(final CardPaymentCompletedEvent event) {
        processInvoiceService.processInvoice(event.getTotal(),
                event.getInstallments(),
                event.getDescription(),
                event.ocurredOn(),
                event.getCardId());
    }
}
