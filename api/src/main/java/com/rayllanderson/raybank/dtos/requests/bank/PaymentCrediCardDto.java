package com.rayllanderson.raybank.dtos.requests.bank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 16, max = 16)
    private String cardNumber;

    @NotNull
    @Size(min = 3, max = 3)
    private String cvv;

    @NotNull
    @FutureOrPresent
    private YearMonth expiration;

    @NotNull
    private PaymentTypeDto paymentType;

    @JsonIgnore
    public boolean isCreditPayment() {
        return PaymentTypeDto.CREDIT_CARD.equals(this.getPaymentType());
    }
}
