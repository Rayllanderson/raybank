package com.rayllanderson.raybank.external.payment.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.models.ExternalTransactionType;

import java.util.Arrays;

public enum ExternalPaymentTypeDto {
    CREDIT_CARD, DEBIT_CARD;

    @JsonCreator
    public static ExternalPaymentTypeDto fromString(String value) {
        try {
            return ExternalPaymentTypeDto.valueOf(value);
        } catch (IllegalArgumentException e) {
            var message =  "Payment type=" + value + " is invalid. Available: " + Arrays.toString(ExternalPaymentTypeDto.values());
            throw new RaybankExternalException.InvalidPaymentMethod(message);
        }
    }

    public ExternalTransactionType toModel() {
        return ExternalTransactionType.valueOf(this.toString());
    }
}
