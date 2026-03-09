package com.eventshub.core.exception;

public class DuplicateEventIdentifierException extends RuntimeException {

    public DuplicateEventIdentifierException(String identifier) {
        super("Identifier: " + identifier + " already exists");
    }
}
