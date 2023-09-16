package com.rayllanderson.raybank.invoice.controllers;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PayInvoiceInternalRequest {
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
