package com.rayllanderson.raybank.external.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayllanderson.raybank.utils.StringUtil;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExternalErrorResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bankStatementId;
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

    public ExternalErrorResponse(String title, Integer status, String rayBankCode, String codeDescription, String message,
                                 String bankStatementId) {
        this.title = title;
        this.status = status;
        this.rayBankCode = rayBankCode;
        this.message = message;
        this.bankStatementId = bankStatementId;
    }

    public static ExternalErrorResponse fromExternalException(RaybankExternalException e) {
        var title = createTitle(e.getReasonValue());
        var statusCode = e.getStatus().value();
        var rayBankCode = e.getRayBankCode();
        var errorDescription = e.getDescription();
        var message = e.getMessage();
        var possibleBankStatement = e.getBankStatement();
        if (possibleBankStatement.isPresent()) {
            var bankStatementId = possibleBankStatement.get().getId();
            return new ExternalErrorResponse(title, statusCode, rayBankCode, errorDescription, message, bankStatementId);
        }
        return new ExternalErrorResponse(title, statusCode, rayBankCode, errorDescription, message);
    }

    private static String createTitle(String title) {
        var titleWithoutUnderscore = title.replace("_", " ");
        return StringUtil.capitalize(titleWithoutUnderscore);
    }
}
