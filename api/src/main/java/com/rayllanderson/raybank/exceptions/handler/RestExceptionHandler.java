package com.rayllanderson.raybank.exceptions.handler;

import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(statusCode).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .status(statusCode.value())
                        .build());
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardError> handleUnprocessableEntityException(UnprocessableEntityException e, HttpServletRequest request) {
        HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(statusCode).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("UNPROCESSABLE_ENTITY")
                        .message(e.getMessage())
                        .path(request.getRequestURI())
                        .status(statusCode.value())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ValidationError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request. Invalid Fields")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .fields(fields)
                        .fieldsMessage(fieldsMessages)
                        .build());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidationError> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(
                ValidationError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request. Invalid Fields")
                        .status(status.value())
                        .fields(fields)
                        .fieldsMessage(fieldsMessages)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleExceptionInternal(Exception ex) {
        log.error("error", ex);
        String title = ex.getCause() != null ? ex.getCause().getMessage() : "erro";
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now())
                .title(title)
                .message("Erro Interno")
                .status(status)
                .build();
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> handleResponseStatusException(ResponseStatusException ex) {
        int status = ex.getStatusCode().value();
        StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now())
                .title(ex.getStatusCode().toString())
                .message(ex.getMessage())
                .status(status)
                .build();
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        int status = HttpStatus.METHOD_NOT_ALLOWED.value();
        StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now())
                .title(HttpStatus.METHOD_NOT_ALLOWED.name())
                .message(ex.getMessage())
                .status(status)
                .build();
        return ResponseEntity.status(status).body(standardError);
    }
}
