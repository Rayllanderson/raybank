package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.pix.controllers.transfer.PixTransferRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PixTransferRequestBuilder {

    public static PixTransferRequest build(String creditKey, BigDecimal amount, String message) {
        PixTransferRequest pixTransferRequest = new PixTransferRequest();
        pixTransferRequest.setCreditKey(creditKey);
        pixTransferRequest.setAmount(amount);
        pixTransferRequest.setMessage(message);
        return pixTransferRequest;
    }
}
