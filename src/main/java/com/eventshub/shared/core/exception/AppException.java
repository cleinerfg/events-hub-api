package com.eventshub.shared.core.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AppException extends RuntimeException {

    private final transient AppError error;

    protected AppException(AppError error, String message) {
        super(message);
        this.error = error;
    }

    public static AppException invalidJson() {
        return new AppException(GlobalAppError.INVALID_JSON, "The provided JSON is invalid");
    }

    public static AppException resourceNotFound(String resource, UUID externalId) {
        return new AppException(GlobalAppError.RESOURCE_NOT_FOUND,
                "The %s with identifier '%s' was not found".formatted(resource, externalId));
    }

    public static AppException invalidPeriod(String startPeriod, String endPeriod) {
        return new AppException(GlobalAppError.INVALID_PERIOD,
                "The period from '%s' to '%s' is not valid".formatted(startPeriod, endPeriod));
    }

    public static AppException systemIntegrity(String message) {
        return new AppException(GlobalAppError.SYSTEM_INTEGRITY_ERROR, message);
    }
}
