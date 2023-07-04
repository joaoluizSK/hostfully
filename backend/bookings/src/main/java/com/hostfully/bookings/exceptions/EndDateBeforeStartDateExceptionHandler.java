package com.hostfully.bookings.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EndDateBeforeStartDateExceptionHandler {

    @ExceptionHandler(EndDateBeforeStartDateException.class)
    public ResponseEntity handleException(EndDateBeforeStartDateException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
}
