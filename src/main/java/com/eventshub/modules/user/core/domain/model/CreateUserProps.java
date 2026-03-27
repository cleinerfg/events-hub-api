package com.eventshub.modules.user.core.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserProps(
        UUID id,
        String name,
        String email,
        String passwordHash
) {
}
