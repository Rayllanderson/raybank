package com.rayllanderson.raybank.invoice.services.credit;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCreditType;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
                LocalDateTime.now());

        if (!invoice.isPaid()) {
            invoice.processCredit(creditInput);
        }

        creditNextInvoice(invoice, creditInput);
    }

    private void creditNextInvoice(Invoice invoice, ProcessInvoiceCredit creditInput) {
        final List<Invoice> allByCardId = invoiceGateway.findAllByCardId(invoice.getCardId());
        final var invoiceList = new InvoiceListHelper(allByCardId);

        final Invoice nextInvoiceToPay = invoiceList.getCurrentInvoiceToPay();
        nextInvoiceToPay.processCredit(creditInput);
    }
}
