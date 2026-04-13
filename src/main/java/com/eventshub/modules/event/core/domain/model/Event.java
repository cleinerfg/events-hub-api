package com.eventshub.modules.event.core.domain.model;

import com.eventshub.shared.core.exception.GlobalAppException;
import com.eventshub.shared.core.support.StringSanitizer;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
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
    private Set<UUID> participantIds = new HashSet<>();

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
                .participantIds(props.participantIds() != null ?
                        new HashSet<>(props.participantIds()) : new HashSet<>()
                )
                .build();
    }

    public void setName(String name) {
        String sanitized = StringSanitizer.capitalize(name);
        if (sanitized == null || sanitized.isBlank())
            throw new IllegalArgumentException(EventMessages.NAME_REQUIRED.getMessage());

        this.name = sanitized;
    }

    public void setType(EventType type) {
        if (type == null)
            throw new IllegalArgumentException(EventMessages.TYPE_REQUIRED.getMessage());
        this.type = type;
    }

    public void setOrganizer(String organizer) {
        String sanitized = StringSanitizer.trimAndClean(organizer);
        if (sanitized == null || sanitized.isBlank())
            throw new IllegalArgumentException(EventMessages.ORGANIZER_REQUIRED.getMessage());

        this.organizer = sanitized;
    }

    public void setLocation(String location) {
        String sanitized = StringSanitizer.trimAndClean(location);
        if (sanitized == null || sanitized.isBlank())
            throw new IllegalArgumentException(EventMessages.LOCATION_REQUIRED.getMessage());

        this.location = sanitized;
    }

    public void setStartDate(OffsetDateTime startDate) {
        if (startDate == null)
            throw new IllegalArgumentException(EventMessages.START_DATE_REQUIRED.getMessage());
        validateEndDate(startDate, endDate);
        this.startDate = startDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        validateEndDate(startDate, endDate);
        this.endDate = endDate;
    }

    public void addParticipant(UUID participantId) {
        if (participantId == null)
            throw new IllegalArgumentException(EventMessages.PARTICIPANT_ID_REQUIRED.getMessage());

        this.participantIds.add(participantId);
    }

    public void removeParticipant(UUID participantId) {
        if (participantId == null)
            throw new IllegalArgumentException(EventMessages.PARTICIPANT_ID_REQUIRED.getMessage());

        if (!participantIds.contains(participantId))
            throw GlobalAppException.resourceNotFound("Participant", participantId);
        participantIds.remove(participantId);
    }

    public boolean participantIsOwner(UUID participantId) {
        if (participantId == null)
            throw new IllegalArgumentException(EventMessages.PARTICIPANT_ID_REQUIRED.getMessage());

        return participantId.equals(ownerId);
    }

    private static void validateId(UUID id) {
        if (id == null)
            throw new IllegalArgumentException(EventMessages.ID_REQUIRED.getMessage());
    }

    private static void validateOwnerId(UUID ownerId) {
        if (ownerId == null)
            throw new IllegalArgumentException(EventMessages.OWNER_ID_REQUIRED.getMessage());
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