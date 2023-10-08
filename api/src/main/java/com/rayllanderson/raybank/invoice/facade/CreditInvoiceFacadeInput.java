package com.rayllanderson.raybank.invoice.facade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreditInvoiceFacadeInput {
    private final String invoiceId;
    private final BigDecimal amount;
    private final String description;
    private final String transactionId;
}
