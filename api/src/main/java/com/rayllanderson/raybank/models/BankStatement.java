package com.rayllanderson.raybank.models;

import com.rayllanderson.raybank.models.enums.BankStatementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime moment;
    @Enumerated(EnumType.STRING)
    private BankStatementType statementType;
    private BigDecimal amount;
    @ManyToOne
    private BankAccount accountSender;
    @ManyToOne
    private BankAccount accountOwner;

    public BankStatement(BankStatementType statementType, BigDecimal amount, BankAccount accountSender, BankAccount accountOwner) {
        this.moment = LocalDateTime.now();
        this.statementType = statementType;
        this.amount = amount;
        this.accountSender = accountSender;
        this.accountOwner = accountOwner;
    }
}
