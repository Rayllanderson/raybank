package com.rayllanderson.raybank.external.exceptions;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum RaybankExternalTypeError {

    CREDIT_CARD_NOT_FOUND("Credit Card not found", "CNF404", NOT_FOUND),
    CREDIT_CARD_BADLY_FORMATTED("Credit Card number is badly formatted", "CMF400", BAD_REQUEST),
    TOKEN_ALREADY_REGISTERED("The client has already registered a token", "TAR422", UNPROCESSABLE_ENTITY),
    TOKEN_UNREGISTERED("The token is not registered. Please, register before proceed", "TUN401", UNAUTHORIZED);

    private final String description;
    private final String rayBankCode;
    private final HttpStatus status;

    RaybankExternalTypeError(String description, String code, HttpStatus status) {
        this.description = description;
        this.rayBankCode = code;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public String getRayBankCode() {
        return rayBankCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
