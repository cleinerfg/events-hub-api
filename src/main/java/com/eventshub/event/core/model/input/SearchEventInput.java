package com.eventshub.event.core.model.input;

import com.eventshub.event.core.model.EventType;

import java.time.OffsetDateTime;

public record SearchEventInput(
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
