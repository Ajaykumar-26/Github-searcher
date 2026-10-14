package com.freightfox.githubsearcher.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(
            IllegalArgumentException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            ConstraintViolationException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, String>> handleMethodValidation(
            HandlerMethodValidationException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid request parameter"));
    }

}
