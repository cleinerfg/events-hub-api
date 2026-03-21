package com.eventshub.shared.core.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter
public class AppException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(AppException.class);

    private final transient AppError error;

    protected AppException(AppError error, String message) {
        super(message);
        this.error = error;
    }

    public static AppException invalidJson() {
        return new AppException(GlobalAppError.INVALID_JSON, "The provided JSON is invalid");
    }

    public static AppException validationFail() {
        return new AppException(GlobalAppError.VALIDATION_FAIL, "The data provided is invalid.");
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
        log.error(message);
        return new AppException(GlobalAppError.SYSTEM_INTEGRITY_ERROR, message);
    }
}
