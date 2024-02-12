package com.rayllanderson.raybank.bankaccount.controllers.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountTransferRequest {
    @NotNull
    private Integer beneficiaryAccountNumber;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    private String message;
}
