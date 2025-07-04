package com.example.springbootjwtauthapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(AuthException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of(
                        "error", Map.of(
                                "code", ex.getCode(),
                                "message", ex.getMessage()
                        )
                ));
    }
}
