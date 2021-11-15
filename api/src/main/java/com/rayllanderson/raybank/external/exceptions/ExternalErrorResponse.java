package com.rayllanderson.raybank.external.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExternalErrorResponse {
    private final String title;
    private final Integer status;
    private final String rayBankCode;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ExternalErrorResponse(String title, Integer status, String rayBankCode, String codeDescription, String message) {
        this.title = title;
        this.status = status;
        this.rayBankCode = rayBankCode;
        this.message = message;
    }

    public static ExternalErrorResponse fromExternalException(RaybankExternalException e) {
        var statusCode = e.getStatus().value();
        var rayBankCode = e.getRayBankCode();
        var errorDescription = e.getDescription();
        var message = e.getMessage();
        return new ExternalErrorResponse(e.getStatus().getReasonPhrase(), statusCode, rayBankCode, errorDescription, message);
    }
}
