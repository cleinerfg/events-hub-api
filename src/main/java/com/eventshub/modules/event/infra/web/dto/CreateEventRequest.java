package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.shared.infra.web.exception.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record CreateEventRequest(
        @NotBlank(message = ValidationMessage.REQUIRED)
        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String name,

        @NotNull(message = ValidationMessage.REQUIRED)
        EventType type,

        String description,

        @NotBlank(message = ValidationMessage.REQUIRED)
        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String organizer,

        @NotBlank(message = ValidationMessage.REQUIRED)
        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String location,

        @NotNull(message = ValidationMessage.REQUIRED)
        OffsetDateTime startDate,

        OffsetDateTime endDate
) {
}
