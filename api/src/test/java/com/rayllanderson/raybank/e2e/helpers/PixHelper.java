package com.rayllanderson.raybank.e2e.helpers;

import com.rayllanderson.raybank.e2e.builders.GenerateQrCodeBuilder;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeOutput;
import com.rayllanderson.raybank.pix.service.qrcode.generate.GenerateQrCodeService;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferInput;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferOutput;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PixHelper {

    @Autowired
    private GenerateQrCodeService qrCodeService;
    @Autowired
    private PixTransferService transferService;

    public GenerateQrCodeOutput generateQrCode(BigDecimal amount, String creditKey, String description) {
        final var input = GenerateQrCodeBuilder.build(amount, creditKey, description);
        return qrCodeService.generate(input);
    }

    public PixTransferOutput doTransfer(PixKey debit, PixKey credit, double amount, String message) {
        return transferService.transfer(new PixTransferInput(debit.getAccountId(), credit.getKey(), BigDecimal.valueOf(amount), message));
    }
}
