package com.eventshub.modules.user.core.application.usecase.command;

import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.Builder;

@Builder
public record LoginCommand(
        String email,
        String password
) {

    public LoginCommand {
        CheckNotNull.forClass(LoginCommand.class.getName())
                .field("email", email)
                .field("password", password);
    }
}
