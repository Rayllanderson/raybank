package com.rayllanderson.raybank.card.controllers.payment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MAX_TRANSACTION_AMOUNT;
import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MIN_TRANSACTION_AMOUNT;

@Getter
@Setter
public class CardPaymentRequest {

    @NotNull
    @DecimalMin(MIN_TRANSACTION_AMOUNT)
    @DecimalMax(MAX_TRANSACTION_AMOUNT)
    private BigDecimal amount;

    @NotBlank
    private String paymentType;

    @NotNull
    @Min(1) @Max(72)
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
