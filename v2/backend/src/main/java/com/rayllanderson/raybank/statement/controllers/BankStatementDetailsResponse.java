package com.rayllanderson.raybank.statement.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BankStatementDetailsResponse {

    private String id;
    private LocalDateTime moment;
    private BigDecimal amount;
    private String description;
    private String message;
    private String type;
    private String method;
    private String financialMovement;
    private String transactionId;
    private DebitResponse debit;
    private CreditResponse credit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InstallmentPlanResponse installmentPlan;

    @Getter
    @Setter
    public static class InstallmentPlanResponse {
        private String id;
        private Integer installments;
    }

    @Getter
    @Setter
    public static class DebitResponse {
        private String name;
        private String origin;
    }

    @Getter
    @Setter
    public static class CreditResponse {
        private String name;
        private String destination;
    }
}
