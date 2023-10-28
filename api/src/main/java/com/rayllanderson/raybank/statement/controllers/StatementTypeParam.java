package com.rayllanderson.raybank.statement.controllers;

import com.rayllanderson.raybank.statement.services.find.BankStatementFinderService;
import com.rayllanderson.raybank.statement.services.find.BankStatementOutput;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import io.github.rayexpresslibraries.ddd.domain.pagination.query.SearchQuery;

import java.util.List;

public enum StatementTypeParam {
    ALL{
        @Override
        public Pagination<BankStatementOutput> find(BankStatementFinderService service, String accountId, SearchQuery query) {
            return service.findAllByAccountId(accountId, query);
        }
    }, CARD{
        @Override
        public Pagination<BankStatementOutput> find(BankStatementFinderService service, String accountId, SearchQuery query) {
            return service.findAllCardStatementsByAccountId(accountId, query);
        }
    }, ACCOUNT {
        @Override
        public Pagination<BankStatementOutput> find(BankStatementFinderService service, String accountId, SearchQuery query) {
            return service.findAllAccountStatementsByAccountId(accountId, query);
        }
    };

    public abstract Pagination<BankStatementOutput> find(BankStatementFinderService service, String accountId, SearchQuery query);

}
