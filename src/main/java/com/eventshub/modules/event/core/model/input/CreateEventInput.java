package com.eventshub.modules.event.core.model.input;

import com.eventshub.modules.event.core.model.EventType;
import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CreateEventInput(
        UUID ownerExternalId,
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {

    public CreateEventInput {
        CheckNotNull.forClass(CreateEventInput.class.getName())
                .field("ownerExternalId", ownerExternalId)
                .field("name", name)
                .field("type", type)
                .field("organizer", organizer)
                .field("location", location)
                .field("startDate", startDate);
    }
}