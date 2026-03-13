package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.model.EventType;

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
