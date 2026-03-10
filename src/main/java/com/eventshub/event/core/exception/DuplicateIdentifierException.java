package com.eventshub.event.core.exception;

public class DuplicateIdentifierException extends RuntimeException {

    public DuplicateIdentifierException(String identifier) {
        super("Identifier: " + identifier + " already exists");
    }
}
