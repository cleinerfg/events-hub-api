package com.eventshub.core.exception;

public class NotFoundEventException extends RuntimeException {
    public NotFoundEventException(String message) {
        super(message);
    }
}
