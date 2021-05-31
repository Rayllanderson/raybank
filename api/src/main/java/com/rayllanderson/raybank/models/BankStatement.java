package com.rayllanderson.raybank.models;

import com.rayllanderson.raybank.models.enums.StatementType;
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
    private StatementType statementType;
    private BigDecimal amount;
    @ManyToOne
    private BankAccount accountSender;
    @ManyToOne
    private BankAccount accountOwner;

    public static BankStatement createTransferStatement(BigDecimal amount, BankAccount accountSender, BankAccount accountOwner){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.TRANSFER)
                .amount(amount)
                .accountSender(accountSender)
                .accountOwner(accountOwner)
                .build();
    }

    public static BankStatement createDepositStatement (BigDecimal amount, BankAccount accountOwner){
        return BankStatement.builder().
                moment(LocalDateTime.now())
                .statementType(StatementType.DEPOSIT)
                .amount(amount)
                .accountSender(null)
                .accountOwner(accountOwner)
                .build();
    }

}
