package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeInput;

import java.math.BigDecimal;

public class GenerateQrCodeInputBuilder {

    public static GenerateQrCodeInput build(BigDecimal amount, String creditKey, String description) {
        return new GenerateQrCodeInput(amount, creditKey, description);
    }
}
