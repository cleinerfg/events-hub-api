package com.eventshub.modules.event.core.application.usecase.command;

import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CreateEventCommand(
        UUID ownerId,
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {

    public CreateEventCommand {
        CheckNotNull.forClass(CreateEventCommand.class.getName())
                .field("ownerId", ownerId)
                .field("name", name)
                .field("type", type)
                .field("organizer", organizer)
                .field("location", location)
                .field("startDate", startDate);
    }
}