package com.rayllanderson.raybank.invoice.services.processinstallment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProcessInstallmentInvoiceInput {
    private final String transactionId;
    private final String cardId;
    private final String establishmentId;
    private final Integer installmentCount;
    private final BigDecimal total;
    private final String description;
    private final LocalDateTime ocurredOn;
}
