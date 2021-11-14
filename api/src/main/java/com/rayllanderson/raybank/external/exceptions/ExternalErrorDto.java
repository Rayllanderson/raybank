package com.rayllanderson.raybank.external.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExternalErrorDto {
    private final String title;
    private final Integer status;
    private final String rayBankCode;
    private final String description;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ExternalErrorDto(String title, Integer status, String rayBankCode, String description) {
        this.title = title;
        this.status = status;
        this.rayBankCode = rayBankCode;
        this.description = description;
    }

    public static ExternalErrorDto fromExternalException(RaybankExternalException e) {
        var statusCode = e.getStatus().value();
        var rayBankCode = e.getRayBankCode();
        var errorDescription = e.getDescription();
        return new ExternalErrorDto(e.getStatus().getReasonPhrase(), statusCode, rayBankCode, errorDescription);
    }
}
