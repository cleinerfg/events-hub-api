package com.eventshub.modules.user.core.application.usecase.command;

import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.Builder;

@Builder
public record CreateUserCommand(
        String name,
        String email,
        String password
) {

    public CreateUserCommand {
        CheckNotNull.forClass(CreateUserCommand.class.getName())
                .field("name", name)
                .field("email", email)
                .field("password", password);
    }
}
