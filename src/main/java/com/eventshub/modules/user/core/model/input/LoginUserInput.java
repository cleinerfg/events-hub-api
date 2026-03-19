package com.eventshub.modules.user.core.model.input;

import lombok.Builder;

@Builder
public record LoginUserInput(
        String email,
        String password
) {
}
