package com.rayllanderson.raybank.statement.models;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.transactions.credit.CardCreditTransaction;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Setter
@Entity
public class CardCreditStatement extends BankStatement {

    private String originId;

    public static BankStatement createCardCreditBankStatement(BigDecimal amount,
                                                              BankAccount accountOwner,
                                                              String transactionId,
                                                              String originId,
                                                              BankStatementType type){
        return CardCreditStatement.builder().
                moment(Instant.now())
                .type(type)
                .amount(amount)
                .message(null)
                .accountOwner(accountOwner)
                .transactionId(transactionId)
                .originId(originId)
                .build();
    }

    public static BankStatement fromCardCreditTransaction(CardCreditTransaction creditTransaction){
        return createCardCreditBankStatement(creditTransaction.getAmount(),
                BankAccount.withId(creditTransaction.getAccountId()),
                creditTransaction.getId(),
                null,
                null);
    }
}
