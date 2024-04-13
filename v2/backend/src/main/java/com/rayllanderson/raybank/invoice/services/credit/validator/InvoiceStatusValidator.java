package com.rayllanderson.raybank.invoice.services.credit.validator;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.models.Invoice;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVOICE_NOT_PAYABLE;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVOICE_PAID;

class InvoiceStatusValidator implements InvoiceValidator{
    @Override
    public void validate(Invoice invoice, BigDecimal amountToBeCredited) {
        if (invoice.isPaid())
            throw UnprocessableEntityException.with(INVOICE_PAID, "Não é possível receber pagamento para fatura já paga.");

        if (invoice.cannotReceivePayment())
            throw UnprocessableEntityException.with(INVOICE_NOT_PAYABLE, "Não é possível receber pagamento para essa fatura. Fatura não está em aberto para receber pagamentos.");
    }
}
