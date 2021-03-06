package com.rayllanderson.raybank.exceptions.handler;

import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ValidationError> handleMethodArgumentNotValid(MethodArgumentNotValidException e, @NonNull HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return ResponseEntity.status(status).body(
                ValidationError.builder()
                        .timestamp(LocalDateTime.now())
                        .title("Bad Request. Invalid Fields")
                        .message(e.getMessage())
                        .path(request.getContextPath())
                        .status(status.value())
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
                        .message(e.getMessage())
                        .status(status.value())
                        .fields(fields)
                        .fieldsMessage(fieldsMessages)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleExceptionInternal(Exception ex) {
        String title = ex.getCause() != null ? ex.getCause().getMessage() : "erro";
        int status = HttpStatus.BAD_GATEWAY.value();
        StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now())
                .title(title)
                .message(ex.getMessage())
                .status(status)
                .build();
        return ResponseEntity.status(status).body(standardError);
    }
}
