package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends RaybankException {

    private UnprocessableEntityException(RaybankExceptionReason reason, String message, HttpStatus status) {
        super(reason, message, status);
    }

    public static UnprocessableEntityException with(RaybankExceptionReason reason, String s) {
        return new UnprocessableEntityException(reason, s, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static UnprocessableEntityException withFormatted(RaybankExceptionReason reason, String s, Object... args) {
        return UnprocessableEntityException.with(reason, String.format(s, args));
    }
}
