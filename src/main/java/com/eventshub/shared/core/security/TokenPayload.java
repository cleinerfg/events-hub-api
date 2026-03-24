package com.eventshub.shared.core.security;

import lombok.Builder;

import java.util.UUID;

@Builder
public record TokenPayload(
        UUID externalId
) {
}
