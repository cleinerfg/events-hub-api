package com.eventshub.modules.user.core.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorCategory;
import com.eventshub.shared.core.exception.ErrorScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserError implements AppError {

    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", ErrorCategory.BUSINESS_RULE_VIOLATION),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", ErrorCategory.UNAUTHORIZED);

    private final String code;
    private final ErrorCategory category;
    private final ErrorScope scope = ErrorScope.USER;
}
