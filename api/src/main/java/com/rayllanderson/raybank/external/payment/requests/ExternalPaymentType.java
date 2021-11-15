package com.rayllanderson.raybank.external.payment.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;

import java.util.Arrays;

import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INVALID_PAYMENT_METHOD;

public enum ExternalPaymentType {
    CREDIT_CARD, DEBIT_CARD;

    @JsonCreator
    public static ExternalPaymentType fromString(String value) {
        try {
            return ExternalPaymentType.valueOf(value);
        } catch (IllegalArgumentException e) {
            var message =  "Payment type=" + value + " is invalid. Available: " + Arrays.toString(ExternalPaymentType.values());
            throw new RaybankExternalException(INVALID_PAYMENT_METHOD, message);
        }
    }
}
