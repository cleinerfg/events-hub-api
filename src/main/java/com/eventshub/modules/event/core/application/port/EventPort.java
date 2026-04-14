package com.eventshub.modules.event.core.application.port;

import com.eventshub.modules.event.core.application.usecase.query.SearchEventQuery;
import com.eventshub.modules.event.core.domain.dto.EventSummary;
import com.eventshub.modules.event.core.domain.dto.ParticipantEvent;
import com.eventshub.modules.event.core.domain.model.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventPort {

    Event create(Event event);

    boolean existsById(UUID id);

    Optional<Event> findById(UUID id);

    List<Event> findAll();

    List<Event> search(SearchEventQuery query);

    Event update(Event event);

    void delete(UUID id);

    List<EventSummary> findAllEventsByOwnerId(UUID ownerId);

    List<ParticipantEvent> findAllParticipants(UUID eventId);

    void addParticipant(UUID eventId, UUID userId);

    void removeParticipant(UUID eventId, UUID userId);
}
