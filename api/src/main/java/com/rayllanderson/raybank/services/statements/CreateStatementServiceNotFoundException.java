package com.rayllanderson.raybank.services.statements;

public class CreateStatementServiceNotFoundException extends RuntimeException {
    public CreateStatementServiceNotFoundException() {
        super("No create statement service was found");
    }
}
