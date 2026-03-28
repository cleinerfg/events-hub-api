package com.eventshub.modules.event.core.domain.model;

import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event {

    private final UUID id;
    private final UUID ownerId;
    private String name;
    private EventType type;

    @Setter
    private String description;

    private String organizer;
    private String location;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    private Event(UUID id, UUID ownerId) {
        validateId(id);
        validateOwnerId(ownerId);

        this.id = id;
        this.ownerId = ownerId;
    }

    public static Event create(CreateEventProps props) {
        var event = new Event(props.id(), props.ownerId());

        event.setName(props.name());
        event.setType(props.type());
        event.setDescription(props.description());
        event.setOrganizer(props.organizer());
        event.setLocation(props.location());
        event.setStartDate(props.startDate());
        event.setEndDate(props.endDate());

        return event;
    }

    public static Event reconstruct(ReconstructEventProps props) {
        return Event.builder()
                .id(props.id())
                .ownerId(props.ownerId())
                .name(props.name())
                .type(props.type())
                .description(props.description())
                .organizer(props.organizer())
                .location(props.location())
                .startDate(props.startDate())
                .endDate(props.endDate())
                .build();
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Event name cannot be blank");
        this.name = name;
    }

    public void setType(EventType type) {
        if (type == null) throw new IllegalArgumentException("Event type cannot be null");
        this.type = type;
    }

    public void setOrganizer(String organizer) {
        if (organizer == null || organizer.isBlank())
            throw new IllegalArgumentException("Event organizer cannot be blank");
        this.organizer = organizer;
    }

    public void setLocation(String location) {
        if (location == null || location.isBlank())
            throw new IllegalArgumentException("Event location cannot be blank");
        this.location = location;
    }

    public void setStartDate(OffsetDateTime startDate) {
        if (startDate == null) throw new IllegalArgumentException("Start date cannot be null");
        validateEndDate(startDate, endDate);
        this.startDate = startDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        validateEndDate(startDate, endDate);
        this.endDate = endDate;
    }

    private static void validateId(UUID id) {
        if (id == null) throw new IllegalArgumentException("Event id cannot be null");
    }

    private static void validateOwnerId(UUID ownerId) {
        if (ownerId == null) throw new IllegalArgumentException("Owner external id cannot be null");
    }

    private static void validateEndDate(OffsetDateTime startDate, OffsetDateTime endDate) {
        /*
         * Was defined `startDate != null` just for NullPointerException safety,
         *  as the creation rule specifies that `startDate` must not be null
         * */
        if (endDate != null && startDate != null && startDate.isAfter(endDate)) {
            throw GlobalAppException.invalidPeriod(startDate.toString(), endDate.toString());
        }
    }
}