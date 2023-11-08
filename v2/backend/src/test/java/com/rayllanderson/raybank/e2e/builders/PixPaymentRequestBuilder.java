package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.pix.controllers.payment.PixPaymentRequest;

public class PixPaymentRequestBuilder {

    public static PixPaymentRequest build(String qrCode) {
        final var r = new PixPaymentRequest();
        r.setQrCode(qrCode);
        return r;
    }
}
