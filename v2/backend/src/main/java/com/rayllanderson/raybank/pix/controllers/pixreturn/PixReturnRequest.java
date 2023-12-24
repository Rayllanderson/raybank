package com.rayllanderson.raybank.pix.controllers.pixreturn;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PixReturnRequest {

    @NotBlank
    private String pixId;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    @Size(max = 140)
    private String message;
}
