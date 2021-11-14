package com.rayllanderson.raybank.external.exceptions;


import org.springframework.http.HttpStatus;

public class RaybankExternalException extends RuntimeException {
    private final RaybankExternalTypeError reason;

    public RaybankExternalException(RaybankExternalTypeError reason) {
        super(reason.getDescription());
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return this.reason.getStatus();
    }

    public String getRayBankCode() {
        return this.reason.getRayBankCode();
    }

    public String getDescription() {
        return this.reason.getDescription();
    }

}
