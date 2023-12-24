package com.rayllanderson.raybank.invoice.services.processinstallment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProcessInstallmentInvoiceInput {
    private final String transactionId;
}
