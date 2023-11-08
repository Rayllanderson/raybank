package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.pix.controllers.qrcode.generate.GenerateQrCodeRequest;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeInput;

import java.math.BigDecimal;

public class GenerateQrCodeBuilder {

    public static GenerateQrCodeInput build(BigDecimal amount, String creditKey, String description) {
        return new GenerateQrCodeInput(amount, creditKey, description);
    }

    public static GenerateQrCodeRequest buildRequest(BigDecimal amount, String creditKey, String description) {
        GenerateQrCodeRequest generateQrCodeRequest = new GenerateQrCodeRequest();
        generateQrCodeRequest.setAmount(amount);
        generateQrCodeRequest.setCreditKey(creditKey);
        generateQrCodeRequest.setDescription(description);
        return generateQrCodeRequest;
    }
}
