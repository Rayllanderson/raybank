package com.rayllanderson.raybank.core.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RaybankException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
    private final RaybankExceptionReason reason;

    protected RaybankException(RaybankExceptionReason reason, String message, HttpStatus status) {
        super(reason.getDescription());
        this.reason = reason;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return (this.message == null || this.message.isEmpty()) ? super.getMessage() : this.message;
    }

    public static RaybankException unauthorized() {
        return new RaybankException(RaybankExceptionReason.UNAUTHORIZED, null, HttpStatus.FORBIDDEN);
    }
}
