package com.eventshub.modules.event.core.application.port;

import com.eventshub.modules.event.core.application.usecase.query.SearchEventQuery;
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

    void addParticipant(UUID eventId, UUID userId);
}
