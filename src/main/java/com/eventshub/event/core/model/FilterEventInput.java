package com.eventshub.event.core.model;

import java.time.OffsetDateTime;

public record FilterEventInput(
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}