package com.rayllanderson.raybank.installment.services.create;


import java.math.BigDecimal;

public record CreateInstallmentPlanInput(String transactionId, String accountId, String invoiceId,
                                         String establishmentId, Integer installmentCount, BigDecimal total,
                                         String description) {
}
