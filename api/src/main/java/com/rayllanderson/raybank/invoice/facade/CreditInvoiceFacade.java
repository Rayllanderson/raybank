package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditService;
import com.rayllanderson.raybank.invoice.services.credit.InvoiceReceiveCreditInput;
import com.rayllanderson.raybank.invoice.services.refund.RefundInvoiceInput;
import com.rayllanderson.raybank.invoice.services.refund.RefundInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditInvoiceFacade {
    private final RefundInvoiceService refundService;
    private final InvoiceCreditService invoiceCreditService;

    public void refund(final RefundInvoiceFacadeInput input) {
        refundService.credit(new RefundInvoiceInput(input.getCardId(),
                input.getAmountToBeCredited(),
                input.getDescription(),
                input.getTransactionId()));
    }

    public void credit(final CreditInvoiceFacadeInput input) {
        invoiceCreditService.credit(new InvoiceReceiveCreditInput(input.getInvoiceId(),
                input.getAmount(),
                input.getDescription(),
                input.getTransactionId()));
    }
}
