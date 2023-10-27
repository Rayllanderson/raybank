package com.rayllanderson.raybank.refund.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RefundRequest {
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
    @NotBlank
    private String reason;
}
