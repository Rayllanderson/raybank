package com.rayllanderson.raybank.bankaccount.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankPaymentDto {

    private String ownerId;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

}
