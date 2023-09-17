package com.rayllanderson.raybank.statement.controllers;

import com.rayllanderson.raybank.statement.services.BankStatementFinderService;

import java.util.List;

public enum StatementTypeParam {
    ALL{
        @Override
        public List<BankStatementDto> find(BankStatementFinderService service, String userId) {
            return service.findAllByUserId(userId);
        }
    }, CARD{
        @Override
        public List<BankStatementDto> find(BankStatementFinderService service, String userId) {
            return service.findAllCardStatementsByUserId(userId);
        }
    }, ACCOUNT {
        @Override
        public List<BankStatementDto> find(BankStatementFinderService service, String userId) {
            return service.findAllAccountStatementsByUserId(userId);
        }
    };

    public abstract List<BankStatementDto> find(BankStatementFinderService service, String userId);

}
