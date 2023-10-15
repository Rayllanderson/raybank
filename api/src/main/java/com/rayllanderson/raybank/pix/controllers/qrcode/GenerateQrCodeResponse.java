package com.rayllanderson.raybank.pix.controllers.qrcode;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GenerateQrCodeResponse {
    private String code;
    private BigDecimal amount;
    private LocalDateTime expiresIn;
    private String description;
}
