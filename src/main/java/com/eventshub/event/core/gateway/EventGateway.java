package com.eventshub.event.core.gateway;

import com.eventshub.event.core.exception.NotFoundException;
import com.eventshub.event.core.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventGateway {

    Event create(Event event);

    boolean existsByIdentifier(String identifier);

    Optional<Event> findByIdentifier(String identifier);

    List<Event> findAll();

    default Event getByIdentifierOrThrow(String identifier) {
        return findByIdentifier(identifier).orElseThrow(() -> new NotFoundException(
                "Event with identifier " + identifier + " not found")
        );
    }
}
