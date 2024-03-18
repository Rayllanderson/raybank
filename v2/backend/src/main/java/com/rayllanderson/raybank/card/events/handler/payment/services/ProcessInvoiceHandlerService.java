package com.rayllanderson.raybank.card.events.handler.payment.services;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInInvoiceService;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInvoiceInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class ProcessInvoiceHandlerService implements CardPaymentHandlerService {

    private final ProcessInstallmentInInvoiceService processInstallmentInInvoiceService;

    @Override
    public void process(final CardCreditPaymentCompletedEvent event) {
        ProcessInstallmentInvoiceInput processInstallmentInvoiceInput = new ProcessInstallmentInvoiceInput(event.getTransactionId());
        processInstallmentInInvoiceService.processInvoice(processInstallmentInvoiceInput);
    }
}
