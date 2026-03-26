package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.model.EventType;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record EventResponse(
        UUID externalId,
        UUID ownerExternalId,
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
