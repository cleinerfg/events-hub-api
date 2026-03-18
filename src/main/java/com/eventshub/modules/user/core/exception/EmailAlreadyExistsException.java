package com.eventshub.modules.user.core.exception;

import com.eventshub.shared.core.exception.AppException;

public class EmailAlreadyExistsException extends AppException {
    public EmailAlreadyExistsException(String email) {
        super(UserError.EMAIL_ALREADY_EXISTS,
                String.format("The email '%s' is already registered. Try signing in.", email)
        );
    }
}