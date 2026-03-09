package com.eventshub.infra.dto;

import com.eventshub.core.enums.EventType;

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
