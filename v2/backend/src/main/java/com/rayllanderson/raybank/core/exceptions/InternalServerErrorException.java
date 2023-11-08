package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RaybankException {

    protected InternalServerErrorException(RaybankExceptionReason reason, String message) {
        super(reason, message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static RaybankException with(RaybankExceptionReason reason, String s) {
        return new InternalServerErrorException(reason, s);
    }

    public static RaybankException withFormatted(RaybankExceptionReason reason, String s, Object ... args) {
        return InternalServerErrorException.with(reason, java.lang.String.format(s, args));
    }
}
