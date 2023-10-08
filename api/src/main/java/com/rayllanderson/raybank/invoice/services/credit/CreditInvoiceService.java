package com.rayllanderson.raybank.invoice.services.credit;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCreditType;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreditInvoiceService {
    private final InvoiceGateway invoiceGateway;

    @Transactional
    public void credit(final CreditInvoiceInput input) {
        final Invoice invoice = invoiceGateway.findById(input.getInvoiceId());

        final var creditInput = new ProcessInvoiceCredit(input.getAmountToBeCredited(),
                InvoiceCreditType.INVOICE_PAYMENT,
                input.getDescription(),
                input.getTransactionId(),
                LocalDate.now());

        invoice.processCredit(creditInput);
    }
}
