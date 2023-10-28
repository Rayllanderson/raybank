package com.rayllanderson.raybank.card.controllers.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rayllanderson.raybank.core.exceptions.BadRequestException;

import java.util.Arrays;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVALID_PAYMENT_TYPE;

public enum PaymentTypeRequest {
    CREDIT, DEBIT;

    @JsonCreator
    public static PaymentTypeRequest fromString(String value) {
        try {
            return PaymentTypeRequest.valueOf(value.toUpperCase());
        } catch (Exception e) {
            var message =  "Payment type=" + value + " is invalid. Available: " + Arrays.toString(PaymentTypeRequest.values());
            throw BadRequestException.with(INVALID_PAYMENT_TYPE, message);
        }
    }
}
