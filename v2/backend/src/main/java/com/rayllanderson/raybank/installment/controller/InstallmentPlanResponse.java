package com.rayllanderson.raybank.installment.controller;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class InstallmentPlanResponse {
    private String id;
    private String transactionId;
    private String originalInvoiceId;
    private String establishmentId;
    private Integer installmentCount;
    private BigDecimal installmentValue;
    private BigDecimal total;
    private BigDecimal refunded;
    private String description;
    private LocalDateTime createdAt;
    private Set<InstallmentResponse> installments;

    @Getter
    @Setter
    public static class InstallmentResponse {
        private String id;
        private String description;
        private BigDecimal value;
        private BigDecimal valueToPay;
        private String status;
        private LocalDate dueDate;
        private String invoiceId;
    }
}
