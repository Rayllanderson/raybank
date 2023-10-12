package com.rayllanderson.raybank.core.exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

    public static BadRequestException formatted(String s, Object ... args) {
        return new BadRequestException(String.format(s, args));
    }
}
