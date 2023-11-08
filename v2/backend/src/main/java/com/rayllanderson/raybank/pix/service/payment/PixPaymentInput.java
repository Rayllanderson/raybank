package com.rayllanderson.raybank.pix.service.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PixPaymentInput {
    private String qrCode;
    private String debitAccountId;
}
