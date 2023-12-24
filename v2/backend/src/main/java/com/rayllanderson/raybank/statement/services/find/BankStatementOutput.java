package com.rayllanderson.raybank.statement.services.find;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BankStatementOutput {

    private String id;
    private LocalDateTime moment;
    private BigDecimal amount;
    private String description;
    private String type;
    private String method;
    private String financialMovement;
    private String transactionId;
    private Debit debit;
    private Credit credit;
    private InstallmentPlan installmentPlan;

    @Getter
    @Setter
    protected static class InstallmentPlan {
        private String id;
        private Integer installments;
    }

    @Getter
    @Setter
    protected static class Debit {
        private String id;
        private String name;
        private String origin;
    }

    @Getter
    @Setter
    protected static class Credit {
        private String id;
        private String name;
        private String destination;
    }
}
