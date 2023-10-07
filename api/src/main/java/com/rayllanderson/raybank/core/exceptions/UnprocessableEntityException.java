package com.rayllanderson.raybank.exceptions;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public static UnprocessableEntityException with(String s) {
        return new UnprocessableEntityException(s);
    }
}
