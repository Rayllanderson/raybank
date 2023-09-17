package com.rayllanderson.raybank.users.services.register;

public class FailedToRegisterException extends RuntimeException {
    public FailedToRegisterException(String s) {
        super(s);
    }
}
