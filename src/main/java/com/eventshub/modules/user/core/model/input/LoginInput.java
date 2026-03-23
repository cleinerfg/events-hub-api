package com.eventshub.modules.user.core.model.input;

import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.Builder;

@Builder
public record LoginInput(
        String email,
        String password
) {

    public LoginInput {
        CheckNotNull.forClass(LoginInput.class.getName())
                .field("email", email)
                .field("password", password);
    }
}
