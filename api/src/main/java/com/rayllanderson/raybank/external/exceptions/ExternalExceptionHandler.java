package com.rayllanderson.raybank.external.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice(basePackages = "com.rayllanderson.raybank.external")
public class ExternalExceptionHandler {

    @ExceptionHandler(RaybankExternalException.class)
    public ResponseEntity<ExternalErrorDto> handleExternalException(RaybankExternalException e) {
        log.error("Handling external exception: {}", e.toString());
        return ResponseEntity.status(e.getStatus()).body(ExternalErrorDto.fromExternalException(e));
    }
}
