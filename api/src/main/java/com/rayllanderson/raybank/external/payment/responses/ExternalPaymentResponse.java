package com.rayllanderson.raybank.external.payment.responses;

import com.rayllanderson.raybank.external.payment.models.ExternalTransaction;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentTypeDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class ExternalPaymentResponse {
    private final String id;
    private final String numberIdentifier;
    private final BigDecimal value;
    private final ExternalPaymentTypeDto paymentMethod;
    private final LocalDateTime timestamp;

    public static ExternalPaymentResponse fromModel(ExternalTransaction ExternalTransaction) {
        var paymentType = ExternalPaymentTypeDto.fromString(ExternalTransaction.getPaymentType().toString());
        return new ExternalPaymentResponse(ExternalTransaction.getId(), ExternalTransaction.getNumberIdentifier(), ExternalTransaction.getValue(),
                paymentType, ExternalTransaction.getTimestamp());
    }
}
