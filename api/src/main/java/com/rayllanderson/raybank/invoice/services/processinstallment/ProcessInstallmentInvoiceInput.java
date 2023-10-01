package com.rayllanderson.raybank.invoice.services.processinstallment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProcessInstallmentInvoiceInput {
    private final String transactionId;
}
