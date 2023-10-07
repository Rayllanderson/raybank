package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.invoice.services.credit.CreditCurrentInvoiceInput;
import com.rayllanderson.raybank.invoice.services.credit.ProcessRefundInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCurrentInvoiceFacade {
    private final ProcessRefundInvoiceService service;

    public void process(final CreditCurrentInvoiceFacadeInput input) {
        service.credit(new CreditCurrentInvoiceInput(input.getCardId(),
                input.getAmountToBeCredited(),
                input.getDescription(),
                input.getTransactionId()));
    }
}
