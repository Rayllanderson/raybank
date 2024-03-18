package com.rayllanderson.raybank.pix.controllers.limit.update;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MAX_TRANSACTION_AMOUNT;

@Getter
@Setter
public class UpdatePixLimitRequest {
    @NotNull
    @PositiveOrZero
    @DecimalMax(MAX_TRANSACTION_AMOUNT)
    private BigDecimal newLimit;
}
