package com.rayllanderson.raybank.invoice.services.credit;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvoiceCreditCurrentService {
    private final InvoiceGateway invoiceGateway;

    @Transactional
    public void credit(InvoiceCreditCurrentInput input) {
        final Invoice invoice = invoiceGateway.findCurrentByCardId(input.getCardId());

        invoice.processCredit(input.getAmountToBeCredited());
    }
}
