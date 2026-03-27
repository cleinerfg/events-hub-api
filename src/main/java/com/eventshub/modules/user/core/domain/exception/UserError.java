package com.eventshub.modules.user.core.domain.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorCategory;
import com.eventshub.shared.core.exception.ErrorScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserError implements AppError {

    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", ErrorCategory.BUSINESS_RULE_VIOLATION),
    INVALID_EMAIL("INVALID_EMAIL", ErrorCategory.INVALID_INPUT),
    INVALID_PASSWORD("INVALID_PASSWORD", ErrorCategory.BUSINESS_RULE_VIOLATION),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", ErrorCategory.SECURITY);

    private final String code;
    private final ErrorCategory category;
    private final ErrorScope scope = ErrorScope.USER;
}
