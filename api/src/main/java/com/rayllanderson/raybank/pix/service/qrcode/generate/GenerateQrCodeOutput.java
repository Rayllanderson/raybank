package com.rayllanderson.raybank.pix.service.qrcode.generate;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GenerateQrCodeOutput {
    private String code;
    private BigDecimal amount;
    private LocalDateTime expiresIn;
    private String description;
}
