package com.rayllanderson.raybank.dtos.requests.bank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rayllanderson.raybank.exceptions.BadRequestException;

import java.util.Arrays;

public enum PaymentTypeDto {
    CREDIT, DEBIT;

    @JsonCreator
    public static PaymentTypeDto fromString(String value) {
        try {
            return PaymentTypeDto.valueOf(value.toUpperCase());
        } catch (Exception e) {
            var message =  "Payment type=" + value + " is invalid. Available: " + Arrays.toString(PaymentTypeDto.values());
            throw new BadRequestException(message);
        }
    }
}
