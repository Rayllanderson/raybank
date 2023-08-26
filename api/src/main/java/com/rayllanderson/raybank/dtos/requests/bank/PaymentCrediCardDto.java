package com.rayllanderson.raybank.dtos.requests.bank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@Builder
public class PaymentCrediCardDto {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotNull
    @Max(16)
    private Long cardNumber;

    @NotNull
    @Max(3)
    private Integer cvv;

    @NotNull
    @FutureOrPresent
    private YearMonth expiration;
}
