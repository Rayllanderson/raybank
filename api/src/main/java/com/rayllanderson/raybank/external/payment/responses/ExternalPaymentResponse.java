package com.rayllanderson.raybank.external.payment.responses;

import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class ExternalPaymentResponse {
    private final String numberIdentifier;
    private final BigDecimal value;
    private final ExternalPaymentType paymentMethod;
    private final LocalDateTime timestamp;

    public static ExternalPaymentResponse fromRequest(ExternalPaymentRequest request) {
        return new ExternalPaymentResponse(request.getNumberIdentifier(), request.getValue(), request.getPaymentMethod(), LocalDateTime.now());
    }
}
