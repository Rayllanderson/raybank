package com.rayllanderson.raybank.invoice.services.credit.validator;

import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceCreditValidator {

    private final List<InvoiceValidator> validators;

    public void validaIfcanReceiveCredit(Invoice invoice, BigDecimal amountToBeCredited) {
        validators.forEach(invoiceValidator -> invoiceValidator.validate(invoice, amountToBeCredited));
    }


}
