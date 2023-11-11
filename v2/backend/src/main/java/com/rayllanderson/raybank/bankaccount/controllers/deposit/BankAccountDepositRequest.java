package com.rayllanderson.raybank.bankaccount.controllers.deposit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountDepositRequest {
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}
