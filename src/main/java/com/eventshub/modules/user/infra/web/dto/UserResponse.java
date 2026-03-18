package com.eventshub.modules.user.infra.web.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID externalId,
        String name,
        String email
) {
}
