package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.invoice.services.credit.CreditInvoiceInput;
import com.rayllanderson.raybank.invoice.services.credit.CreditInvoiceService;
import com.rayllanderson.raybank.invoice.services.refund.RefundInvoiceInput;
import com.rayllanderson.raybank.invoice.services.refund.RefundInvoiceService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditInvoiceFacade {
    private final RefundInvoiceService refundService;
    private final CreditInvoiceService creditService;

    public void refund(final RefundInvoiceFacadeInput input) {
        refundService.credit(new RefundInvoiceInput(input.getCardId(),
                input.getAmountToBeCredited(),
                input.getDescription(),
                input.getTransactionId()));
    }

    public void credit(final CreditInvoiceFacadeInput input) {
        creditService.credit(new CreditInvoiceInput(input.getInvoiceId(),
                input.getAmount(),
                input.getDescription(),
                input.getTransactionId()));
    }
}
