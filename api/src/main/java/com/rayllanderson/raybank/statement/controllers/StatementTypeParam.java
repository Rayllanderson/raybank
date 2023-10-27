package com.rayllanderson.raybank.statement.controllers;

import com.rayllanderson.raybank.statement.services.find.BankStatementFinderService;
import com.rayllanderson.raybank.statement.services.find.BankStatementOutput;

import java.util.List;

public enum StatementTypeParam {
    ALL{
        @Override
        public List<BankStatementOutput> find(BankStatementFinderService service, String accountId) {
            return service.findAllByAccountId(accountId);
        }
    }, CARD{
        @Override
        public List<BankStatementOutput> find(BankStatementFinderService service, String accountId) {
            return service.findAllCardStatementsByAccountId(accountId);
        }
    }, ACCOUNT {
        @Override
        public List<BankStatementOutput> find(BankStatementFinderService service, String accountId) {
            return service.findAllAccountStatementsByAccountId(accountId);
        }
    };

    public abstract List<BankStatementOutput> find(BankStatementFinderService service, String accountId);

}
