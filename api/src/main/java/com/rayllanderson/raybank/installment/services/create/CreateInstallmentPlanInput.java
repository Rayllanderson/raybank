package com.rayllanderson.raybank.installment.services.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class CreateInstallmentPlanInput {
    private final String transactionId;
    private final String accountId;
    private final String invoiceId;
    private final String establishmentId;
    private final Integer installmentCount;
    private final BigDecimal total;
    private final String description;
}
