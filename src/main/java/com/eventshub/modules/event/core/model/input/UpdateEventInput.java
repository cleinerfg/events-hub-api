package com.eventshub.modules.event.core.model.input;

import com.eventshub.modules.event.core.model.EventType;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record UpdateEventInput(
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
