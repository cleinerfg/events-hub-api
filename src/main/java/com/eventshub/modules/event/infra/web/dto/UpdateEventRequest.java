package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.shared.infra.web.exception.ValidationMessage;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record UpdateEventRequest(

        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String name,

        EventType type,
        String description,

        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String organizer,

        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String location,

        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
