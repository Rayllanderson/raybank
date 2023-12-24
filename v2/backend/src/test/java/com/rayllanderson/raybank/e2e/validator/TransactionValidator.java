package com.rayllanderson.raybank.e2e.validator;

import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.assertThat;

public interface TransactionValidator {

    default ListAssert<Transaction> assertThatTransactionsFromAccount(String accountId) {
        final var allByAccountId = getTransactionRepository().findAllByAccountId(accountId);
        return assertThat(allByAccountId);
    }

    TransactionRepository getTransactionRepository();
}
