package com.rayllanderson.raybank.transaction.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transaction {

    @Id
    protected String id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    protected LocalDateTime moment;

    @Column(nullable = false)
    protected BigDecimal amount;

    protected String referenceId;

    protected String description;

    @Column(nullable = false)
    protected String accountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected TransactionType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected TransactionMethod method;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected FinancialMovement financialMovement;
    @Embedded
    protected Debit debit;

    @Embedded
    protected Credit credit;

    @PrePersist
    public void createId() {
        if (Objects.isNull(this.id)) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
