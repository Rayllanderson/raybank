package com.rayllanderson.raybank.statement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class BankStatement {

    @Id
    private String id;
    private LocalDateTime moment;
    private BigDecimal amount;
    private String description;
    private String type;
    private String method;
    private String financialMovement;
    private String transactionId;
    private String accountId;
    @Embedded
    private InstallmentPlan installmentPlan;
    @Embedded
    private Debit debit;
    @Embedded
    private Credit credit;

    @Getter
    @Setter
    @Embeddable
    public static class InstallmentPlan {
        @Column(name = "installment_id")
        private String id;
        private Integer installments;
    }

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Debit {
        @Column(name = "debit_id")
        private String id;
        @Column(name = "debit_name")
        private String name;
        @Column(name = "debit_origin")
        private String origin;
    }

    @Getter
    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credit {
        @Column(name = "credit_id")
        private String id;
        @Column(name = "credit_name")
        private String name;
        @Column(name = "credit_destination")
        private String destination;
    }
}
