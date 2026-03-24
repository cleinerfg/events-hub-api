package com.eventshub.shared.core.exception;

import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public abstract class AppException extends RuntimeException {

    private final transient AppError error;
    private final transient Set<String> fails = new LinkedHashSet<>();

    protected AppException(AppError error, String message) {
        super(message);
        this.error = error;
    }

    protected void addFails(Set<String> fails) {
        this.fails.addAll(fails);
    }
}
