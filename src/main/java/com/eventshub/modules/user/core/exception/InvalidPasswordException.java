package com.eventshub.modules.user.core.exception;

import com.eventshub.shared.core.exception.AppException;
import lombok.Getter;

import java.util.Set;

@Getter
public class InvalidPasswordException extends AppException {

    public InvalidPasswordException(Set<String> fails) {
        super(UserError.INVALID_PASSWORD, "The password provided is invalid.");
        addFails(fails);
    }
}
