package com.eventshub.modules.user.infra.web.dto;

public record LoginResponse(
        String token,
        String tokenType
) {
    public LoginResponse(String token) {
        this(token, "Bearer");
    }
}
