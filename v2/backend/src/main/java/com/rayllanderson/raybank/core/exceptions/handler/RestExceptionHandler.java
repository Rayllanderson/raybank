package com.rayllanderson.raybank.core.exceptions.handler;

import com.rayllanderson.raybank.core.exceptions.RaybankException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(RaybankException.class)
    public ResponseEntity<StandardError> handlerRaybankException(RaybankException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getStatus()).body(StandardError.from(e, request.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardError.from(new RuntimeException("Required request body is missing"), HttpStatus.BAD_REQUEST, request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handlerMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardError.from(e, request.getRequestURI()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<StandardError> handlerBindException(BindException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardError.from(e, request.getRequestURI()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        int status = HttpStatus.METHOD_NOT_ALLOWED.value();
        return ResponseEntity.status(status).body(StandardError.from(ex, request.getRequestURI()));
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<StandardError> handlerHttpRequestMethodNotSupportedException(org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
        final var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(StandardError.from(ex, request.getRequestURI()));
    }

    @ExceptionHandler({ConstraintViolationException.class, MissingServletRequestPartException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<StandardError> handler4xxException(Exception e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardError.badRequest(e, request.getRequestURI()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<StandardError> handlerSizeLimitExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(StandardError.badRequest("Tamanho máximo excedido. Selecione um arquivo menor.", request.getRequestURI()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handlerExceptionInternal(Exception e, HttpServletRequest request) {
        log.error("erro desconhecido", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(StandardError.from(request.getRequestURI()));
    }
}
