package com.rayllanderson.raybank.external.payment.services;


import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.responses.ExternalPaymentResponse;

public interface ExternalPaymentMethod {

    ExternalPaymentResponse pay(ExternalPaymentRequest request);
}
