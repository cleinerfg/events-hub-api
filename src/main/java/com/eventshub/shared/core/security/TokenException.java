package com.eventshub.shared.core.security;

import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.core.exception.GlobalAppError;

public class TokenException extends AppException {

    private TokenException(GlobalAppError error) {
        super(error, "Provide a valid Bearer Token.");
    }

    public static TokenException required() {
        return new TokenException(GlobalAppError.TOKEN_REQUIRED);
    }

    public static TokenException invalid() {
        return new TokenException(GlobalAppError.TOKEN_INVALID);
    }

    public static TokenException expired() {
        return new TokenException(GlobalAppError.TOKEN_EXPIRED);
    }
}
