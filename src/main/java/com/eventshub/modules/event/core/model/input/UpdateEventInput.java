package com.eventshub.modules.event.core.model.input;

import com.eventshub.modules.event.core.model.EventType;

import java.time.OffsetDateTime;

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
