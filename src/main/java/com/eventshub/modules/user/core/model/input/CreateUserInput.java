package com.eventshub.modules.user.core.model.input;

public record CreateUserInput(
        String name,
        String email,
        String password
) {

    public CreateUserInput {
        email = (email != null) ? email.trim().toLowerCase() : null;
    }
}
