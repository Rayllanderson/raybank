package com.rayllanderson.raybank.statement.services.create;

public class CreateStatementServiceNotFoundException extends RuntimeException {
    public CreateStatementServiceNotFoundException() {
        super("No create statement service was found");
    }
}
