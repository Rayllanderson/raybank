package com.rayllanderson.raybank.invoice.services.refund;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class RefundInvoiceInput {
    private final String cardId;
    private final BigDecimal amountToBeCredited;
    private final String description;
    private final String transactionId;
}
