package com.eventshub.modules.user.infra.web.dto;

import com.eventshub.shared.infra.web.exception.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = ValidationMessage.REQUIRED)
        @Email(message = ValidationMessage.INVALID_EMAIL_FORMAT)
        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String email,

        @NotBlank(message = ValidationMessage.REQUIRED)
        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String password
) {
}
