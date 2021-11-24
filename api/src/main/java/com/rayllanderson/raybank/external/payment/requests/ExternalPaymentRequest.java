package com.rayllanderson.raybank.external.payment.requests;

import com.rayllanderson.raybank.external.payment.models.ExternalTransaction;
import com.rayllanderson.raybank.external.validator.TokenExists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ToString
@Getter
@RequiredArgsConstructor
public class ExternalPaymentRequest {

    @NotEmpty
    @TokenExists
    private final String token;

    @NotNull
    private final ExternalPaymentTypeDto paymentMethod;

    @NotEmpty
    private final String numberIdentifier;

    @NotNull
    @DecimalMin(value = "0.1")
    private final BigDecimal value;

    public ExternalTransaction toModel() {
        return new ExternalTransaction(numberIdentifier, paymentMethod.toModel(), value);
    }
}
