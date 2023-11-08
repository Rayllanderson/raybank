package com.rayllanderson.raybank.external.exceptions;

import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@ToString
public enum RaybankExternalTypeError {

    CREDIT_CARD_NOT_FOUND("Credit Card not found", "CNF404", NOT_FOUND),
    DEBIT_CARD_NOT_FOUND("Debit Card not found", "DNF404", NOT_FOUND),
    CARD_BADLY_FORMATTED("Card number is badly formatted", "CBF400", BAD_REQUEST),
    INSUFFICIENT_CREDIT_CARD_LIMIT("Credit Card has no available limit", "ICL422", UNPROCESSABLE_ENTITY),
    INSUFFICIENT_ACCOUNT_BALANCE("Client has no available balance", "IAC422", UNPROCESSABLE_ENTITY),
    INVALID_PAYMENT_METHOD("The payment method is not valid", "IPM400", BAD_REQUEST),
    TOKEN_ALREADY_REGISTERED("The client has already registered a token", "TAR422", UNPROCESSABLE_ENTITY),
    TOKEN_UNREGISTERED("The token is not registered. Please, register before proceed", "TUN401", UNAUTHORIZED),
    TOKEN_INVALID("The token is not valid.", "TNV401", UNAUTHORIZED),
    TRANSACTION_NOT_FOUND("Transaction not found", "TNF404", NOT_FOUND);

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
