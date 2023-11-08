package com.rayllanderson.raybank.statement.controllers;

import io.github.rayexpresslibraries.ddd.domain.pagination.query.Property;

public class StatementProperty extends Property {

    private static final String[] properties = {"id", "moment", "amount", "type", "method", "credit_name", "debit_name", "financial_movement"};

    public StatementProperty() {
        super(properties);
    }
}
