package com.rayllanderson.raybank.card.controllers.limit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChangeCardLimitRequest {
    @NotNull
    @PositiveOrZero
    private BigDecimal newLimit;
}
