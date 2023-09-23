package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.transaction.models.Transaction;

public interface CreateStatementService {

    void process(Transaction transaction);
    boolean supports(Transaction transaction);
}
