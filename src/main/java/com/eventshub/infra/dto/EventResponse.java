package com.eventshub.infra.dto;

import com.eventshub.core.enums.EventType;
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
