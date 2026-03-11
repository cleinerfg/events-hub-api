package com.eventshub.event.core.exception;

public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException(String period1, String period2) {
        super("Period is not valid: " + period1 + " and " + period2);
    }
}
