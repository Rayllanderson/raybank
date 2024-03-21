package com.rayllanderson.raybank.invoice.services.credit.validator;

import com.rayllanderson.raybank.invoice.models.Invoice;

import java.math.BigDecimal;

interface InvoiceValidator {
    void validate(Invoice invoice, BigDecimal amountToBeCredited);
}
