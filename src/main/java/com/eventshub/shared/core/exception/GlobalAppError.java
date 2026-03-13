package com.eventshub.shared.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalAppError implements AppError {

    SYSTEM_INTEGRITY_ERROR("SYSTEM_INTEGRITY_ERROR", ErrorCategory.INTERNAL_SERVER_ERROR),
    INVALID_JSON("INVALID_JSON", ErrorCategory.INVALID_INPUT),
    DUPLICATE_IDENTIFIER("DUPLICATE_IDENTIFIER", ErrorCategory.BUSINESS_RULE_VIOLATION),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", ErrorCategory.NOT_FOUND),
    INVALID_PERIOD("INVALID_PERIOD", ErrorCategory.INVALID_INPUT);

    private final String code;
    private final ErrorCategory category;
    private final ErrorScope scope = ErrorScope.GLOBAL;
}
