package com.eventshub.modules.user.core.exception;

import com.eventshub.shared.core.exception.AppException;

public class InvalidEmailException extends AppException {

    public InvalidEmailException(String email) {
        super(UserError.INVALID_EMAIL,
                "The email '%s' provided is invalid.".formatted(email)
        );
    }
}
