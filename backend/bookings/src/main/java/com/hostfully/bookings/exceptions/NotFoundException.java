package com.hostfully.bookings.exceptions;

public class NotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Record not found";
    }
}
