package com.rayllanderson.raybank.card.controllers.limit;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MAX_TRANSACTION_AMOUNT;
import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MIN_TRANSACTION_AMOUNT;

@Getter
@Setter
public class ChangeCardLimitRequest {
    @NotNull
    @PositiveOrZero
    @DecimalMax(MAX_TRANSACTION_AMOUNT)
    private BigDecimal limit;
}
