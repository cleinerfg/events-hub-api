package com.eventshub.modules.event.core.application.usecase.command;

import com.eventshub.modules.event.core.domain.model.EventType;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record UpdateEventCommand(
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
