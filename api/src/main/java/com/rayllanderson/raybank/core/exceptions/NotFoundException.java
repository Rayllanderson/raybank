package com.rayllanderson.raybank.core.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException formatted(String s, Object ... args) {
        return new NotFoundException(String.format(s, args));
    }
}
