package com.rayllanderson.raybank.boleto.factory;

import com.rayllanderson.raybank.boleto.models.Beneficiary;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.services.credit.BoletoCreditInput;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.facade.CreditInvoiceFacade;
import com.rayllanderson.raybank.invoice.facade.CreditInvoiceFacadeInputMapper;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
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

        final Beneficiary beneficiary = new Beneficiary(invoice.getId(), BeneficiaryType.INVOICE, invoice);
        validate(beneficiary);

        return beneficiary;
    }

    @Override
    public void validate(Beneficiary beneficiary) {
        final Invoice invoice = (Invoice) beneficiary.getData();

        if (invoice.cannotReceivePayment()) {
            throw UnprocessableEntityException.with("Não é possível gerar boleto para fatura, pois fatura não pode receber pagamentos");
        }
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
