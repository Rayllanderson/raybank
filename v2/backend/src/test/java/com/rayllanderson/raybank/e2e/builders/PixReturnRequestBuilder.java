package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.pix.controllers.pixreturn.PixReturnRequest;

import java.math.BigDecimal;

public class PixReturnRequestBuilder {

    public static PixReturnRequest build(String pixId, double amount, String message) {
        PixReturnRequest request = new PixReturnRequest();
        request.setPixId(pixId);
        request.setAmount(BigDecimal.valueOf(amount));
        request.setMessage(message);
        return request;
    }

    public static PixReturnRequest build(String pixId, BigDecimal amount, String message) {
        PixReturnRequest request = new PixReturnRequest();
        request.setPixId(pixId);
        request.setAmount(amount);
        request.setMessage(message);
        return request;
    }
}
