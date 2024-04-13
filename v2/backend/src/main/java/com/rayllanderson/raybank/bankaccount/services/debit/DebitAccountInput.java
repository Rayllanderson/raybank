package com.rayllanderson.raybank.bankaccount.services.debit;

import com.rayllanderson.raybank.shared.dtos.Destination;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DebitAccountInput {

    private String accountId;
    private BigDecimal amount;
    private String description;
    private String message;
    private DebitTransaction transaction;
    private Destination destination;

    @Getter
    @Setter
    public static class DebitTransaction {
        private String referenceId;
        private TransactionType transactionType;
        private TransactionMethod transactionMethod;
    }
}
