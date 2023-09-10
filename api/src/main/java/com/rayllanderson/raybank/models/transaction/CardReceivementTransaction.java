package com.rayllanderson.raybank.models.transaction;

import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Setter
@Entity
public class CardReceivementTransaction extends Transaction {
    @OneToOne
    private Transaction originalTransaction;

    public CardReceivementTransaction(Instant moment, TransactionType type, BigDecimal amount, String message, BankAccount accountSender, BankAccount accountOwner, Transaction originalTransaction) {
        super(null, moment, type, amount, message, accountSender, accountOwner);
        this.originalTransaction = originalTransaction;
    }

    public static CardReceivementTransaction fromTransaction(Transaction transaction, Transaction originalTransaction) {
        return new CardReceivementTransaction(
                transaction.getMoment(),
                TransactionType.CARD_RECEIVE_PAYMENT,
                transaction.getAmount(),
                transaction.getMessage(),
                null,
                transaction.getAccountOwner(),
                originalTransaction
        );
    }
}
