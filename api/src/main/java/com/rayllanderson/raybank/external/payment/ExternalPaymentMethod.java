package com.rayllanderson.raybank.external.payment;


import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;

public interface ExternalPaymentMethod {

    void pay(ExternalPaymentRequest request); //TODO: Criar response
}
