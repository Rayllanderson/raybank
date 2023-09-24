package com.rayllanderson.raybank.transaction.controllers;

import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.services.TransactionFinderService;

import java.util.List;

public enum StatementTypeParam {
    ALL{
        @Override
        public List<Transaction> find(TransactionFinderService service, String accountId) {
            return service.findAllByAccountId(accountId);
        }
    }, CARD{
        @Override
        public List<Transaction> find(TransactionFinderService service, String accountId) {
            return service.findAllCardStatementsByUserId(accountId);
        }
    }, ACCOUNT {
        @Override
        public List<Transaction> find(TransactionFinderService service, String accountId) {
            return service.findAllAccountStatementsByAccountId(accountId);
        }
    };

    public abstract List<Transaction> find(TransactionFinderService service, String accountId);

}
