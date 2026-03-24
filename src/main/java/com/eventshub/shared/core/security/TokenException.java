package com.eventshub.shared.core.security;

import com.eventshub.shared.core.exception.AppException;

public class TokenException extends AppException {

    private TokenException(TokenError error) {
        super(error, "Provide a valid Bearer Token.");
    }

    public static TokenException required() {
        return new TokenException(TokenError.TOKEN_REQUIRED);
    }

    public static TokenException invalid() {
        return new TokenException(TokenError.TOKEN_INVALID);
    }

    public static TokenException expired() {
        return new TokenException(TokenError.TOKEN_EXPIRED);
    }
}
