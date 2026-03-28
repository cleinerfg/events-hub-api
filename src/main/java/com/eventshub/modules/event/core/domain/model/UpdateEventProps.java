package com.eventshub.modules.event.core.domain.model;

import java.time.OffsetDateTime;

public record UpdateEventProps(
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}
