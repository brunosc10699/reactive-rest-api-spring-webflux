package com.bruno.bootcampheroes.exceptions;

import com.bruno.bootcampheroes.resources.exceptions.ValidationError;
import com.bruno.bootcampheroes.services.exceptions.ExistingResourceException;
import com.bruno.bootcampheroes.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception) {
        int status = HttpStatus.NOT_FOUND.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status)
                .error("Not Found")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity<StandardError> existingResource (ExistingResourceException exception) {
        int status = HttpStatus.BAD_REQUEST.value();
        StandardError error = StandardError.builder()
                .timestamp(Instant.now())
                .status(status)
                .error("Bad Request")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<StandardError> validation(WebExchangeBindException exception) {
        int status = HttpStatus.BAD_REQUEST.value();
        ValidationError error = ValidationError.builder()
                .timestamp(Instant.now())
                .status(status)
                .error("Bad Request")
                .message(exception.getMessage())
                .build();
        for (FieldError field : exception.getBindingResult().getFieldErrors()) {
            error.addError(field.getField(), field.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}
