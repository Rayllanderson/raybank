package com.rayllanderson.raybank.core.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends RaybankException {

    private InternalServerErrorException(RaybankExceptionReason reason, String message, HttpStatus status) {
        super(reason, message, status);
    }

    public static InternalServerErrorException with(RaybankExceptionReason reason, String s) {
        return new InternalServerErrorException(reason, s, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
