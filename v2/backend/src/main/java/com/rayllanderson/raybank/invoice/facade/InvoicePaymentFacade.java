package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.card.events.CardCreditPaymentCompletedEvent;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.core.exceptions.InternalServerErrorException;
import com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInInvoiceService;
import com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInstallmentInvoiceInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InvoicePaymentFacade {

    private final InvoiceGateway invoiceGateway;
    private final ProcessInstallmentInInvoiceService processInstallmentInInvoiceService;

    public void verifyPaymentFor(Card card) {
        Optional<Invoice> currentByCardId = Optional.ofNullable(invoiceGateway.findCurrentByCardId(card.getId()));

        if (currentByCardId.isEmpty()) {
            throw InternalServerErrorException.with(RaybankExceptionReason.INVOICE_NOT_AVAILABLE, "Fatura indisponível para processar pagamento");
        }
    }

    public void processInvoice(CardCreditPaymentCompletedEvent event) {
        ProcessInstallmentInvoiceInput processInstallmentInvoiceInput = new ProcessInstallmentInvoiceInput(event.getTransactionId());
        processInstallmentInInvoiceService.processInvoice(processInstallmentInvoiceInput);
    }
}
