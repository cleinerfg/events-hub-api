package com.eventshub.modules.user.core.domain.exception;

import com.eventshub.shared.core.exception.AppException;

public class InvalidCredentialsException extends AppException {

    public InvalidCredentialsException() {
        super(UserError.INVALID_CREDENTIALS, "Incorrect email or password.");
    }
}
