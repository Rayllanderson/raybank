package com.rayllanderson.raybank.bankaccount.controllers.transfer;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MAX_TRANSACTION_AMOUNT;
import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MIN_TRANSACTION_AMOUNT;

@Getter
@Setter
public class BankAccountTransferRequest {
    @NotNull
    private Integer beneficiaryAccountNumber;

    @NotNull
    @DecimalMin(MIN_TRANSACTION_AMOUNT)
    @DecimalMax(MAX_TRANSACTION_AMOUNT)
    private BigDecimal amount;
    private String message;
}
