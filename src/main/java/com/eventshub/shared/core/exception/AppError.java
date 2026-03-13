package com.eventshub.shared.core.exception;

public interface AppError {
    String getCode();

    ErrorCategory getCategory();

    ErrorScope getScope();
}

