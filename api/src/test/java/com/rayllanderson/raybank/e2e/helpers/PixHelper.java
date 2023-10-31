package com.rayllanderson.raybank.e2e.helpers;

import com.rayllanderson.raybank.e2e.builders.GenerateQrCodeInputBuilder;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeOutput;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PixHelper {

    @Autowired
    private GenerateQrCodeService qrCodeService;

    public GenerateQrCodeOutput generateQrCode(BigDecimal amount, String creditKey, String description) {
        final var input = GenerateQrCodeInputBuilder.build(amount, creditKey, description);
        return qrCodeService.generate(input);
    }
}
