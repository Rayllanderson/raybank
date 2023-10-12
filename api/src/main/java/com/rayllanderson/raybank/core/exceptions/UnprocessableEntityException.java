package com.rayllanderson.raybank.core.exceptions;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public static UnprocessableEntityException with(String s) {
        return new UnprocessableEntityException(s);
    }

    public static UnprocessableEntityException withFormatted(String s, Object... args) {
        return new UnprocessableEntityException(String.format(s, args));
    }
}
