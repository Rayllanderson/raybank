package com.rayllanderson.raybank.boleto.services.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoletoPaymentInput {
    private String barCode;
    private String payerId;
}
