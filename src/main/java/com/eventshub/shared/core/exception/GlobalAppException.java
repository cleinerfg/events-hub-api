package com.eventshub.shared.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class GlobalAppException extends AppException {

    private static final Logger log = LoggerFactory.getLogger(GlobalAppException.class);

    private GlobalAppException(GlobalAppError error, String message) {
        super(error, message);
    }

    public static GlobalAppException invalidJson() {
        return new GlobalAppException(GlobalAppError.INVALID_JSON, "The provided JSON is invalid");
    }

    public static GlobalAppException invalidParam() {
        return new GlobalAppException(GlobalAppError.INVALID_PARAM, "The provided param is invalid");
    }

    public static GlobalAppException invalidQueryParams() {
        return new GlobalAppException(GlobalAppError.INVALID_QUERY_PARAMS, "The provided query params is invalid");
    }

    public static GlobalAppException validationFail() {
        return new GlobalAppException(GlobalAppError.VALIDATION_FAIL, "The data provided is invalid.");
    }

    public static GlobalAppException resourceNotFound(String resource, UUID externalId) {
        return new GlobalAppException(GlobalAppError.RESOURCE_NOT_FOUND,
                "The %s with identifier '%s' was not found".formatted(resource, externalId));
    }

    public static GlobalAppException invalidPeriod(String startPeriod, String endPeriod) {
        return new GlobalAppException(GlobalAppError.INVALID_PERIOD,
                "The period from '%s' to '%s' is not valid".formatted(startPeriod, endPeriod));
    }

    public static GlobalAppException systemIntegrity(String message) {
        log.error(message);
        var publicMessage = "System Integrity Violation";
        return new GlobalAppException(GlobalAppError.SYSTEM_INTEGRITY_ERROR, publicMessage);
    }
}
