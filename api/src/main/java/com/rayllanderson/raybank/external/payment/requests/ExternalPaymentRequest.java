package com.rayllanderson.raybank.external.payment.requests;

import com.rayllanderson.raybank.external.payment.models.ExternalTransaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Getter
@RequiredArgsConstructor
public class ExternalPaymentRequest {

    @NotEmpty
    private final String token;

    @NotNull
    private final ExternalPaymentTypeDto paymentMethod;

    @NotEmpty
    private final String numberIdentifier;

    @NotNull
    @DecimalMin(value = "0.1")
    private final BigDecimal value;

    public ExternalTransaction toModel() {
        return new ExternalTransaction(numberIdentifier, value, paymentMethod.toModel(), token);
    }
}
