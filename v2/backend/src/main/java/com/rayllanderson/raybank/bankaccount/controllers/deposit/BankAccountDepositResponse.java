package com.rayllanderson.raybank.bankaccount.controllers.deposit;

import com.rayllanderson.raybank.transaction.models.Transaction;

import java.math.BigDecimal;

public record BankAccountDepositResponse(String transactionId, BigDecimal amount) {
    public static BankAccountDepositResponse from(final Transaction transaction) {
        return new BankAccountDepositResponse(transaction.getId(), transaction.getAmount());
    }
}
