package com.rayllanderson.raybank.invoice.facade;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class InvoiceCreditCurrentFacadeInput {
    private final String cardId;
    private final BigDecimal amountToBeCredited;
}
