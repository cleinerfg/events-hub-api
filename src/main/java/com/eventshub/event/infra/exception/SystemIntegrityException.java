package com.eventshub.event.infra.exception;

public class SystemIntegrityException extends RuntimeException {
    public SystemIntegrityException(String message) {
        super(message);
    }
}
