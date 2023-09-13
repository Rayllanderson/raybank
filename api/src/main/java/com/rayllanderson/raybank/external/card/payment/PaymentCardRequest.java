package com.rayllanderson.raybank.external.card.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
@Setter
@Builder
public class PaymentCardRequest {

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    private PaymentTypeRequest paymentType;

    @NotNull
    @Min(1)
    private Integer installments;

    @NotNull
    private LocalDateTime ocurredOn;

    @NotNull
    private String description;

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
