package com.eventshub.event.core;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record Event(
        Long id,
        String identifier,
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
