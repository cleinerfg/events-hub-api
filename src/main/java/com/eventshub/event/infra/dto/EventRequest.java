package com.eventshub.event.infra.dto;

import com.eventshub.event.core.EventType;

import java.time.OffsetDateTime;

public record EventRequest(
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
