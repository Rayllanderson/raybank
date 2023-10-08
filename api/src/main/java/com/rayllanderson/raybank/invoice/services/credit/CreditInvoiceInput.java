package com.rayllanderson.raybank.invoice.services.credit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreditInvoiceInput {
    private final String invoiceId;
    private final BigDecimal amountToBeCredited;
    private final String description;
    private final String transactionId;
}
