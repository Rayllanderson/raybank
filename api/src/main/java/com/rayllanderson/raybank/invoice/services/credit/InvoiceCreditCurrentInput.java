package com.rayllanderson.raybank.invoice.services.credit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class InvoiceCreditCurrentInput {
    private final String cardId;
    private final BigDecimal amountToBeCredited;
}
