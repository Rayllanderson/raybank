package com.rayllanderson.raybank.card.controllers.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCreditCardRequest {
    @NotNull
    @PositiveOrZero
    private BigDecimal limit;
    @NotNull
    private Integer dueDay;
}
