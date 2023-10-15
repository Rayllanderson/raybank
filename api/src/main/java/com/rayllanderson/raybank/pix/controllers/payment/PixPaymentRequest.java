package com.rayllanderson.raybank.pix.controllers.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PixPaymentRequest {

    @NotBlank
    private String qrCode;
}
