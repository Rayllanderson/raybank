package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.models.transaction.Transaction;

public interface CreateStatementService {

    void process(Transaction transaction);
    boolean supports(Transaction transaction);
}
