package com.eventshub.modules.user.infra.web.dto;

public record LoginRequest(
        String email,
        String password
) {
}
