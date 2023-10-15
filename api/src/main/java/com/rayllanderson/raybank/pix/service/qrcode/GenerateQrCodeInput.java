package com.rayllanderson.raybank.pix.service.qrcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class GenerateQrCodeInput {
    private BigDecimal amount;
    private String creditAccountId;
    private String description;
}
