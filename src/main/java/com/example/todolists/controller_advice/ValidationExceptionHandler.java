package com.example.todolists.controller_advice;

import com.example.todolists.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.ValueInstantiationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundError(
            ResourceNotFoundException ex
    ) {
        return new ResponseEntity<>(
                Map.of("error", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidJson(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        System.out.println("Cause: " + cause);
        System.out.println("Cause of cause: " + (cause != null ? cause.getCause() : null));
        if (cause instanceof ValueInstantiationException ife && ife.getCause() instanceof IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(Map.of("error", iae.getMessage()));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid body"));
    }
}
