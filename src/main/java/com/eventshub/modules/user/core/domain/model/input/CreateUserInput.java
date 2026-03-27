package com.eventshub.modules.user.core.domain.model.input;

import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.Builder;

@Builder
public record CreateUserInput(
        String name,
        String email,
        String password
) {

    public CreateUserInput {
        CheckNotNull.forClass(CreateUserInput.class.getName())
                .field("name", name)
                .field("email", email)
                .field("password", password);
    }
}
