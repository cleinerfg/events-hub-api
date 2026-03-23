package com.eventshub.modules.user.core.exception;

import com.eventshub.shared.core.exception.AppException;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class InvalidPasswordException extends AppException {

    private final transient Set<String> fails = new LinkedHashSet<>();

    public InvalidPasswordException(Set<String> fails) {
        super(UserError.INVALID_PASSWORD, "The password provided is invalid.");
        addFails(fails);
    }

    private void addFails(Set<String> fails) {
        this.fails.addAll(fails);
    }
}
