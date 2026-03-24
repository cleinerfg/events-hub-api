package com.eventshub.modules.event.core.model;

import com.eventshub.modules.event.core.model.input.CreateEventInput;
import com.eventshub.modules.event.core.model.input.UpdateEventInput;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class Event {

    private Long id;
    private final UUID externalId;
    private String name;
    private EventType type;
    private String description;
    private String organizer;
    private String location;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    public static Event create(CreateEventInput input, UUID externalId) {
        var event = Event.builder()
                .externalId(externalId)
                .name(input.name())
                .type(input.type())
                .description(input.description())
                .organizer(input.organizer())
                .location(input.location())
                .startDate(input.startDate())
                .endDate(input.endDate())
                .build();

        event.validateEndDate();
        return event;
    }

    public void update(UpdateEventInput input) {
        applyPartialUpdates(input);
        validateEndDate();
    }

    private void applyPartialUpdates(UpdateEventInput input) {
        if (input.name() != null) this.name = input.name();
        if (input.type() != null) this.type = input.type();
        if (input.description() != null) this.description = input.description();
        if (input.organizer() != null) this.organizer = input.organizer();
        if (input.location() != null) this.location = input.location();
        if (input.startDate() != null) this.startDate = input.startDate();
        if (input.endDate() != null) this.endDate = input.endDate();
    }

    private void validateEndDate() {
        if (endDate != null && startDate.isAfter(endDate)) {
            throw GlobalAppException.invalidPeriod(startDate.toString(), endDate.toString());
        }
    }
}