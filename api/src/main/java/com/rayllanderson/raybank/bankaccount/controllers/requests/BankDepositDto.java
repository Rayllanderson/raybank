package com.rayllanderson.raybank.bankaccount.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDepositDto {

    private String ownerId;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

}
