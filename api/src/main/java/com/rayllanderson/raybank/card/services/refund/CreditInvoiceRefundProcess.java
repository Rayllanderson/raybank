package com.rayllanderson.raybank.card.services.refund;

import com.rayllanderson.raybank.invoice.facade.CreditCurrentInvoiceFacade;
import com.rayllanderson.raybank.invoice.facade.CreditCurrentInvoiceFacadeInputMapper;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
class CreditInvoiceRefundProcess implements RefundCardProcess {

    private final CreditCurrentInvoiceFacade creditInvoiceFacade;
    private final CreditCurrentInvoiceFacadeInputMapper creditInvoiceFacadeInputMapper;

    @Override
    public void refund(Transaction transaction, BigDecimal amount) {
        final var creditInvoiceInput = creditInvoiceFacadeInputMapper.from(transaction, amount);
        creditInvoiceFacade.process(creditInvoiceInput);
    }
}
