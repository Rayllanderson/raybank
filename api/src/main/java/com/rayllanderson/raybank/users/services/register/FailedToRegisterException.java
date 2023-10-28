package com.rayllanderson.raybank.users.services.register;

import com.rayllanderson.raybank.core.exceptions.InternalServerErrorException;
import com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason;

public class FailedToRegisterException extends InternalServerErrorException {
    public FailedToRegisterException(String s) {
        super(RaybankExceptionReason.INTERNAL_SERVER_ERROR, s);
    }
}
