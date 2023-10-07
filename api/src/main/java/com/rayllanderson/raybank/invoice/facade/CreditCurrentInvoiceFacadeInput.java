package com.rayllanderson.raybank.invoice.facade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreditCurrentInvoiceFacadeInput {
    private final String cardId;
    private final BigDecimal amountToBeCredited;
    private final String description;
    private final String transactionId;
}
