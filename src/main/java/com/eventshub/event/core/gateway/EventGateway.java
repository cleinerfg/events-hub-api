package com.eventshub.event.core.gateway;

import com.eventshub.event.core.exception.NotFoundException;
import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.model.FilterEventInput;

import java.util.List;
import java.util.Optional;

public interface EventGateway {

    Event create(Event event);

    boolean existsByIdentifier(String identifier);

    Optional<Event> findByIdentifier(String identifier);

    List<Event> findAll();

    List<Event> findAllWithFilter(FilterEventInput filter);

    Event update(Event event);

    void delete(String identifier);

    default Event getByIdentifierOrThrow(String identifier) {
        return findByIdentifier(identifier).orElseThrow(() -> createNotFoundException(identifier));
    }

    default NotFoundException createNotFoundException(String identifier) {
        return new NotFoundException("Event with identifier " + identifier + " not found");
    }
}
