package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RaybankException {

    protected UnprocessableEntityException(RaybankExceptionReason reason, String message) {
        super(reason, message, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static RaybankException with(RaybankExceptionReason reason, String s) {
        return new UnprocessableEntityException(reason, s);
    }

    public static RaybankException withFormatted(RaybankExceptionReason reason, String s, Object ... args) {
        return UnprocessableEntityException.with(reason, java.lang.String.format(s, args));
    }
}