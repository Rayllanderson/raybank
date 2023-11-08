package com.rayllanderson.raybank.bankaccount.controllers.transfer;

import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BankAccountTransferResponse {
    private final BigDecimal amount;
    private final int to;
    private final TransactionResponse transaction;

    @AllArgsConstructor
    @Getter
    protected static class TransactionResponse {
        String id;
        LocalDateTime moment;
        String type;
    }

    public static BankAccountTransferResponse from(final Transaction transaction, int beneficiaryNumber) {
        final var transactionResponse = new TransactionResponse(transaction.getId(), transaction.getMoment(), transaction.getType().name());
        return new BankAccountTransferResponse(transaction.getAmount(), beneficiaryNumber, transactionResponse);
    }
}
