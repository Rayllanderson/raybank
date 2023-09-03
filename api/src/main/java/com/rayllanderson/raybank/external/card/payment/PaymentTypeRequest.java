package com.rayllanderson.raybank.external.card.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rayllanderson.raybank.exceptions.BadRequestException;

import java.util.Arrays;

public enum PaymentTypeRequest {
    CREDIT, DEBIT;

    @JsonCreator
    public static PaymentTypeRequest fromString(String value) {
        try {
            return PaymentTypeRequest.valueOf(value.toUpperCase());
        } catch (Exception e) {
            var message =  "Payment type=" + value + " is invalid. Available: " + Arrays.toString(PaymentTypeRequest.values());
            throw new BadRequestException(message);
        }
    }
}