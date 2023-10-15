package com.rayllanderson.raybank.pix.service.qrcode.generate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class GenerateQrCodeInput {
    private BigDecimal amount;
    private String creditKey;
    private String description;
}
