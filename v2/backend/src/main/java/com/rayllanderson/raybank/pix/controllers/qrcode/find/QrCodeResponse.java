package com.rayllanderson.raybank.pix.controllers.qrcode.find;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class QrCodeResponse {
    private String id;
    private String code;
    private BigDecimal amount;
    private String status;
    private String creditKey;
    private LocalDateTime expiresIn;
    private String description;
}
