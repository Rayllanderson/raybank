package com.rayllanderson.raybank.pix.controllers.qrcode.generate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MAX_TRANSACTION_AMOUNT;
import static com.rayllanderson.raybank.shared.constants.TransactionConstants.MIN_TRANSACTION_AMOUNT;

@Getter
@Setter
public class GenerateQrCodeRequest {

    @NotNull
    @DecimalMin(MIN_TRANSACTION_AMOUNT)
    @DecimalMax(MAX_TRANSACTION_AMOUNT)
    private BigDecimal amount;
    @NotBlank
    private String creditKey;
    @Size(max = 140)
    private String description;
}
