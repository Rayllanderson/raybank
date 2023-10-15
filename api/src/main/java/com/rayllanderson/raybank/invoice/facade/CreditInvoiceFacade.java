package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.invoice.services.payment.InvoicePaymentService;
import com.rayllanderson.raybank.invoice.services.payment.InvoiceReceiveCreditInput;
import com.rayllanderson.raybank.invoice.services.refund.RefundInvoiceInput;
import com.rayllanderson.raybank.invoice.services.refund.RefundInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditInvoiceFacade {
    private final RefundInvoiceService refundService;
    private final InvoicePaymentService invoicePaymentService;

    public void refund(final RefundInvoiceFacadeInput input) {
        refundService.credit(new RefundInvoiceInput(input.getCardId(),
                input.getAmountToBeCredited(),
                input.getDescription(),
                input.getTransactionId()));
    }

    public void credit(final CreditInvoiceFacadeInput input) {
        invoicePaymentService.receiveCredit(new InvoiceReceiveCreditInput(input.getInvoiceId(),
                input.getAmount(),
                input.getDescription(),
                input.getTransactionId()));
    }
}
