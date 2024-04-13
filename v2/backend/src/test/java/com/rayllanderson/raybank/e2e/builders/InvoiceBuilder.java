package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditInput;

import java.math.BigDecimal;

public class InvoiceBuilder {

    public static InvoiceCreditInput build(BigDecimal amount, String accountId, String cardId, String invoiceId) {
        return new InvoiceCreditInput(amount, accountId, invoiceId);
    }
}
