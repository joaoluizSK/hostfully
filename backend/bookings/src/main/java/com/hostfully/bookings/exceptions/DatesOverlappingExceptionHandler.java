package com.hostfully.bookings.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatesOverlappingExceptionHandler {

    @ExceptionHandler(DatesOverlappingException.class)
    public ResponseEntity handleException(DatesOverlappingException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
}
