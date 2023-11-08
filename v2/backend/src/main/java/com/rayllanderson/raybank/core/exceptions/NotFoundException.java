package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RaybankException {

    protected NotFoundException(RaybankExceptionReason reason, String message) {
        super(reason, message, HttpStatus.NOT_FOUND);
    }

    public static RaybankException with(RaybankExceptionReason reason, String s) {
        return new NotFoundException(reason, s);
    }

    public static RaybankException withFormatted(RaybankExceptionReason reason, String s, Object ... args) {
        return NotFoundException.with(reason, java.lang.String.format(s, args));
    }
}
