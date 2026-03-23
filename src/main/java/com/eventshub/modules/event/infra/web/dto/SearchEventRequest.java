package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.model.EventType;
import com.eventshub.shared.infra.web.exception.ValidationMessage;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SearchEventRequest(
        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String name,

        EventType type,
        String description,

        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String organizer,

        @Size(max = 255, message = ValidationMessage.MAX_LENGTH_255)
        String location,

        LocalDate startDateFrom,
        LocalDate startDateUntil,
        LocalDate endDateFrom,
        LocalDate endDateUntil
) {
}
