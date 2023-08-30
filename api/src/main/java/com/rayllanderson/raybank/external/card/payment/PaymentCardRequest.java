package com.rayllanderson.raybank.external.card.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@Builder
public class PaymentCardRequest {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private PaymentTypeRequest paymentType;

    @Valid
    @NotNull
    private Card card;

    @Getter
    @Setter
    @Builder
    public static class Card {
        @NotNull
        @Size(min = 16, max = 16)
        private String number;

        @NotNull
        @Size(min = 3, max = 3)
        private String securityCode;

        @NotNull
        @FutureOrPresent
        private YearMonth expiryDate;
    }
}
