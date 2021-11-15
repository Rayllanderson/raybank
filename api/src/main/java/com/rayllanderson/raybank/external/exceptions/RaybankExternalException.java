package com.rayllanderson.raybank.external.exceptions;


import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class RaybankExternalException extends RuntimeException {
    private final RaybankExternalTypeError reason;
    private String message;

    public RaybankExternalException(RaybankExternalTypeError reason) {
        super(reason.getDescription());
        this.reason = reason;
    }

    public RaybankExternalException(RaybankExternalTypeError reason, String message) {
        super(reason.getDescription());
        this.reason = reason;
        this.message = message;
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

    @Override
    public String getMessage() {
        return (this.message == null || this.message.isEmpty()) ? super.getMessage() : this.message;
    }
}
