package com.eventshub.modules.user.core.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserMessages {

    ID_REQUIRED("User id cannot be null"),
    EMAIL_REQUIRED("User email cannot be blank"),
    NAME_REQUIRED("User name cannot be blank"),
    PASSWORD_REQUIRED("Password hash cannot be blank");

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}