package com.hostfully.bookings.exceptions;

public class EndDateBeforeStartDateException extends Exception {
        @Override
        public String getMessage() {
            return "End Date is before Start Date";
        }

}
