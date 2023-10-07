package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InvoiceBeneficary implements BeneficiaryTypeFinder {

    private final InvoiceGateway invoiceGateway;

    @Override
    public Beneficiary find(final String id) {
        final var invoice = invoiceGateway.findById(id);

        return new Beneficiary(invoice.getId(), BeneficiaryType.INVOICE, invoice);
    }

    @Override
    public boolean supports(final BeneficiaryType type) {
        return BeneficiaryType.INVOICE.equals(type);
    }
}
