package com.rayllanderson.raybank.external.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice(basePackages = "com.rayllanderson.raybank.external")
public class ExternalExceptionHandler {

    @ExceptionHandler(RaybankExternalException.class)
    public ResponseEntity<ExternalErrorDto> handleExternalException(RaybankExternalException e) {
        return ResponseEntity.status(e.getStatus()).body(ExternalErrorDto.fromExternalException(e));
    }
}
