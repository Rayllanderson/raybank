package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.invoice.facade.CreditInvoiceFacade;
import com.rayllanderson.raybank.invoice.facade.CreditInvoiceFacadeInputMapper;
import com.rayllanderson.raybank.invoice.facade.RefundInvoiceFacadeInput;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InvoiceBeneficaryService implements BeneficiaryTypeService {

    private final InvoiceGateway invoiceGateway;
    private final CreditInvoiceFacade creditInvoiceFacade;
    private final CreditInvoiceFacadeInputMapper mapper;

    @Override
    public Beneficiary find(final String id) {
        final var invoice = invoiceGateway.findById(id);

        return new Beneficiary(invoice.getId(), BeneficiaryType.INVOICE, invoice);
    }

    @Override
    public void receiveCredit(BoletoCreditInput input) {
        final var creditInput = mapper.from(input);
        creditInvoiceFacade.credit(creditInput);
    }

    @Override
    public boolean supports(final BeneficiaryType type) {
        return BeneficiaryType.INVOICE.equals(type);
    }
}
