package com.rayllanderson.raybank.transaction.gateway;

import com.rayllanderson.raybank.transaction.models.Transaction;

import java.util.Optional;

public interface TransactionGateway {
    Transaction findById(final String id);
    Transaction findByCreditId(final String creditId);

    Transaction save(Transaction transaction);

    Optional<Transaction> findByReferenceIdAndAccountId(String referenceId, String accountId);
}
