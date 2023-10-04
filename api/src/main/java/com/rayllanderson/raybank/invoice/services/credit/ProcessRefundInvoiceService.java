package com.rayllanderson.raybank.invoice.services.credit;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditInvoiceCurrentService {
    private final InvoiceGateway invoiceGateway;

    @Transactional
    public void credit(CreditCurrentInvoiceInput input) {
        final Invoice invoice = invoiceGateway.findCurrentByCardId(input.getCardId());

        invoice.processCredit(input.getAmountToBeCredited());
    }
}
