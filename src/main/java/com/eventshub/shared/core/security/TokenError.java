package com.eventshub.shared.core.security;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorCategory;
import com.eventshub.shared.core.exception.ErrorScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenError implements AppError {

    TOKEN_EXPIRED("TOKEN_EXPIRED", ErrorCategory.SECURITY),
    TOKEN_INVALID("TOKEN_INVALID", ErrorCategory.SECURITY),
    TOKEN_REQUIRED("TOKEN_REQUIRED", ErrorCategory.SECURITY);

    private final String code;
    private final ErrorCategory category;
    private final ErrorScope scope = ErrorScope.GLOBAL;
}
