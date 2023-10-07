package com.rayllanderson.raybank.transaction.gateway;

import com.rayllanderson.raybank.transaction.models.Transaction;

public interface TransactionGateway {
    Transaction findById(final String id);
}
