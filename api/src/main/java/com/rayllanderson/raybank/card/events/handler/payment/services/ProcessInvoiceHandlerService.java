package com.rayllanderson.raybank.card.events.handler.payment.services;

import com.rayllanderson.raybank.card.events.CardPaymentCompletedEvent;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInvoiceInput;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessInvoiceHandlerService implements CardPaymentHandlerService {

    private final ProcessInstallmentInInvoiceService processInstallmentInInvoiceService;

    @Override
    public void process(final CardPaymentCompletedEvent event) {
        ProcessInstallmentInvoiceInput processInstallmentInvoiceInput = new ProcessInstallmentInvoiceInput(
                event.getTransactionId(),
                event.getCardId(),
                event.getEstablishmentId(),
                event.getInstallments(),
                event.getTotal(),
                event.getDescription(),
                event.ocurredOn());
        processInstallmentInInvoiceService.processInvoice(processInstallmentInvoiceInput);
    }
}
