package com.rayllanderson.raybank.bankaccount.controllers.deposit;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MAX_TRANSACTION_AMOUNT;
import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MIN_TRANSACTION_AMOUNT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDepositRequest {
    @NotNull
    @DecimalMin(MIN_TRANSACTION_AMOUNT)
    @DecimalMax(MAX_TRANSACTION_AMOUNT)
    private BigDecimal amount;
}
