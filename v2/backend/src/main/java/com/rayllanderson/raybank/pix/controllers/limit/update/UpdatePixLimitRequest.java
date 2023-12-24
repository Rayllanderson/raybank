package com.rayllanderson.raybank.pix.controllers.limit.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdatePixLimitRequest {
    @NotNull
    @PositiveOrZero
    private BigDecimal newLimit;
}
