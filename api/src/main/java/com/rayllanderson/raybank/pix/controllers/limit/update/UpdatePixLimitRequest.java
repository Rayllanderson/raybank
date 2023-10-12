package com.rayllanderson.raybank.pix.controllers.limit.update;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdatePixLimitRequest {
    @PositiveOrZero
    private BigDecimal newLimit;
}
