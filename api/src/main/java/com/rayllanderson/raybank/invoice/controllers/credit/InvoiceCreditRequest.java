package com.rayllanderson.raybank.invoice.controllers.credit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreditRequest {
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
