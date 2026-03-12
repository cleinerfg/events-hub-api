package com.eventshub.event.core.model;

import com.eventshub.event.core.model.input.UpdateEventInput;
import com.eventshub.shared.exception.InvalidPeriodException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Event {

    private Long id;
    private String identifier;
    private String name;
    private EventType type;
    private String description;
    private String organizer;
    private String location;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    public void update(UpdateEventInput input) {
        applyPartialUpdates(input);
        validatePeriod();
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

    private void validatePeriod() {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPeriodException(
                    "startDate: " + startDate,
                    "endDate:" + endDate
            );
        }
    }
}