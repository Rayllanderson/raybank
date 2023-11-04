package com.rayllanderson.raybank.core.exceptions.handler;

import com.rayllanderson.raybank.core.exceptions.RaybankException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    protected String title;
    protected Integer status;
    protected String message;
    protected RayBankError raybankError;
    protected LocalDateTime timestamp;
    protected String path;
    protected List<FieldError> fieldErrors;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class RayBankError {
        String code;
        String description;
    }

    public static StandardError from(RaybankException e, String path) {
        final var reason = e.getReason();
        final var raybankErrror = new RayBankError(reason.getCode(), reason.getDescription());
        return new StandardError(e.getStatus().name(), e.getStatus().value(), e.getMessage(), raybankErrror, LocalDateTime.now(), path, emptyList());
    }

    public static StandardError from(String path) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new StandardError(status.name(), status.value(), "Erro Interno", null, LocalDateTime.now(), path, emptyList());
    }

    public static StandardError from(HttpRequestMethodNotSupportedException e, String path) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        return new StandardError(status.name(), status.value(), e.getMessage(), null, LocalDateTime.now(), path, emptyList());
    }

    public static StandardError from(Exception e, HttpStatus status, String path) {
        return new StandardError(status.name(), status.value(), e.getMessage(), null, LocalDateTime.now(), path, emptyList());
    }

    public static StandardError from(AccessDeniedException e, String path) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new StandardError(status.name(), status.value(), e.getMessage(), null, LocalDateTime.now(), path, emptyList());
    }

    public static StandardError from(BindException e, String path) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new StandardError(status.name(), status.value(), "Campos inválidos", null, LocalDateTime.now(), path, getFieldErrors(e));
    }

    private static List<FieldError> getFieldErrors(final BindException e) {
        List<org.springframework.validation.FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(err -> new FieldError(err.getField(), err.getDefaultMessage())).collect(Collectors.toList());
    }
}
