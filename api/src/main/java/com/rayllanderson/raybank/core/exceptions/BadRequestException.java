package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RaybankException {

    protected BadRequestException(RaybankExceptionReason reason, String message) {
        super(reason, message, HttpStatus.BAD_REQUEST);
    }

    public static RaybankException with(RaybankExceptionReason reason, String s) {
        return new BadRequestException(reason, s);
    }

    public static RaybankException withFormatted(RaybankExceptionReason reason, String s, Object ... args) {
        return BadRequestException.with(reason, java.lang.String.format(s, args));
    }
}
