package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RaybankException {

    private NotFoundException(RaybankExceptionReason reason, String message, HttpStatus status) {
        super(reason, message, status);
    }

    public static NotFoundException with(RaybankExceptionReason reason, String s) {
        return new NotFoundException(reason, s, HttpStatus.NOT_FOUND);
    }
    
    public static NotFoundException formatted(RaybankExceptionReason reason, String s, Object ... args) {
        return NotFoundException.with(reason, String.format(s, args));
    }
}
