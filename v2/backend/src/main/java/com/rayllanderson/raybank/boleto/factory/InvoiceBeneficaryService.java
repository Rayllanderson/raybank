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

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVOICE_NOT_PAYABLE;

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
    public String getName(String id) {
        return "Fatura";
    }

    @Override
    public void validate(final Beneficiary beneficiary) {
        final Invoice invoice = (Invoice) beneficiary.getData();

        if (invoice.cannotReceivePayment()) {
            throw UnprocessableEntityException.with(INVOICE_NOT_PAYABLE, "Não é possível gerar boleto para fatura, pois fatura não pode receber pagamentos");
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
