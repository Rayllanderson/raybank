package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InvoiceBeneficary implements BeneficiaryTypeFinder {

    private final InvoiceGateway invoiceGateway;
    private final ProcessInvoiceCredit processInvoiceCredit;

    @Override
    public Beneficiary find(final String id) {
        final var invoice = invoiceGateway.findById(id);

        return new Beneficiary(invoice.getId(), BeneficiaryType.INVOICE, invoice);
    }

    @Override
    public Transaction receiveCredit(BoletoCreditInput input) {
        return null;
    }

    @Override
    public boolean supports(final BeneficiaryType type) {
        return BeneficiaryType.INVOICE.equals(type);
    }
}
