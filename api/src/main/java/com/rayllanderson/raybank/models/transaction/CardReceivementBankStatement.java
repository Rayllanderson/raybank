package com.rayllanderson.raybank.models.transaction;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.BankStatementType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Setter
@Entity
public class CardReceivementBankStatement extends com.rayllanderson.raybank.models.BankStatement {
    @OneToOne
    private com.rayllanderson.raybank.models.BankStatement originalBankStatement;

    public CardReceivementBankStatement(Instant moment, BankStatementType type, BigDecimal amount, String message, BankAccount accountSender, BankAccount accountOwner, BankStatement originalBankStatement) {
        super(null, moment, type, amount, message, accountSender, accountOwner);
        this.originalBankStatement = originalBankStatement;
    }

    public static CardReceivementBankStatement fromBankStatement(BankStatement bankStatement, BankStatement originalBankStatement) {
        return new CardReceivementBankStatement(
                bankStatement.getMoment(),
                BankStatementType.CARD_RECEIVE_PAYMENT,
                bankStatement.getAmount(),
                bankStatement.getMessage(),
                null,
                bankStatement.getAccountOwner(),
                originalBankStatement
        );
    }
}
