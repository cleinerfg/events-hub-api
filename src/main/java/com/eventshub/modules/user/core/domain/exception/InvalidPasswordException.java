package com.eventshub.modules.user.core.domain.exception;

import com.eventshub.modules.user.core.domain.validator.PasswordError;
import com.eventshub.shared.core.exception.AppException;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class InvalidPasswordException extends AppException {

    public InvalidPasswordException(Set<PasswordError> fails) {
        super(UserError.INVALID_PASSWORD, "The password provided is invalid.");
        addFails(fails.stream()
                .map(PasswordError::name)
                .collect(Collectors.toSet()
                )
        );
    }
}
