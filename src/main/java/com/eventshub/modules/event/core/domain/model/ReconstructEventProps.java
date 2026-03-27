package com.eventshub.modules.event.core.domain.model;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ReconstructEventProps(
        UUID id,
        UUID ownerId,
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}