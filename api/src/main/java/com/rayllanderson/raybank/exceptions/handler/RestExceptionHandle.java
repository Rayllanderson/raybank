package com.rayllanderson.raybank.exceptions.handler;

import com.rayllanderson.raybank.exceptions.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandle extends ResponseEntityExceptionHandler {


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

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException e, @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status, WebRequest request) {
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

    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, @NonNull HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        String title = ex.getCause() != null ? ex.getCause().getMessage() : "erro";
        StandardError standardError = StandardError.builder().timestamp(LocalDateTime.now())
                .title(title)
                .message(ex.getMessage())
                .path(request.getContextPath())
                .status(status.value())
                .build();
        return new ResponseEntity<>(standardError, headers, status);
    }
}
