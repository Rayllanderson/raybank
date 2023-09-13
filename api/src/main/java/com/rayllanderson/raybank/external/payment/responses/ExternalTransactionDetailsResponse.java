package com.rayllanderson.raybank.external.payment.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayllanderson.raybank.external.payment.models.ExternalTransaction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class ExternalTransactionDetailsResponse {
    private final String id;
    private final String paymentType;
    private final BigDecimal value;
    private final LocalDateTime timestamp;
    private final String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    public static ExternalTransactionDetailsResponse fromModel(ExternalTransaction bankStatement) {
        log.info("Convertendo transação externa {} para {}", bankStatement.getId(), ExternalTransactionDetailsResponse.class.getSimpleName());
        var response = new ExternalTransactionDetailsResponse(
                bankStatement.getId(),
                bankStatement.getPaymentType().toString(),
                bankStatement.getValue(),
                bankStatement.getTimestamp(),
                bankStatement.getSituation().getStatus().toString(),
                bankStatement.getSituation().getMessage()
        );
        log.info("Transação externa convertida {}", response);
        return response;
    }
}
