package com.hostfully.bookings.exceptions;

public class DatesOverlappingException extends Exception {

    @Override
    public String getMessage() {
        return "Dates are overlapping";
    }
}
