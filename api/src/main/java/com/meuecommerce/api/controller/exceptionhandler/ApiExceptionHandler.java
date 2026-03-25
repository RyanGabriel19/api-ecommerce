package com.meuecommerce.api.controller.exceptionhandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidation(MethodArgumentNotValidException ex) {
        
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String detail = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        Problem problem = new Problem(
            status.value(),
            LocalDateTime.now(),
            "Um ou mais campos estão inválidos: " + detail
        );

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleIllegalArgument(IllegalArgumentException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = new Problem (
            status.value(),
            LocalDateTime.now(),
            ex.getMessage()
        );

        return ResponseEntity.status(status).body(problem);
    }
}
