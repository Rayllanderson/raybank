package com.rayllanderson.raybank.boleto.controllers.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoletoPaymentRequest {
    @NotBlank
    private String barCode;
}
