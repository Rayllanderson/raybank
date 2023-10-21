package com.rayllanderson.raybank.invoice.services.credit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class InvoiceReceiveCreditInput {
    private final String invoiceId;
    private final BigDecimal amount;
    private final String description;
    private final String debitTransactionId;
}
