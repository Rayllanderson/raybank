package com.rayllanderson.raybank.e2e.helpers;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.e2e.builders.InvoiceBuilder;
import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditInput;
import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InvoiceHelper {
    @Autowired
    private InvoiceCreditService invoiceCreditService;

    public Transaction payCurrentInvoice(BigDecimal amount, String accountId, String cardId) {
        InvoiceCreditInput creditInput = InvoiceBuilder.build(amount, accountId, cardId, null);
        return invoiceCreditService.creditCurrent(creditInput);
    }

    public Transaction payCurrentInvoice(BigDecimal amount, Card card) {
        InvoiceCreditInput creditInput = InvoiceBuilder.build(amount, card.getAccountId(), card.getId(), null);
        return invoiceCreditService.creditCurrent(creditInput);
    }

}
