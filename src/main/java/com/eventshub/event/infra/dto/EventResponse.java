package com.eventshub.event.infra.dto;

import com.eventshub.event.core.EventType;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record EventResponse(
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
