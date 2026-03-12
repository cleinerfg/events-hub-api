package com.eventshub.event.core.port;

import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.model.input.SearchEventInput;
import com.eventshub.shared.core.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface EventPort {

    Event create(Event event);

    boolean existsByIdentifier(String identifier);

    Optional<Event> findByIdentifier(String identifier);

    List<Event> findAll();

    List<Event> search(SearchEventInput input);

    Event update(Event event);

    void delete(String identifier);

    default Event getByIdentifierOrThrow(String identifier) {
        return findByIdentifier(identifier).orElseThrow(() -> createNotFoundException(identifier));
    }

    default NotFoundException createNotFoundException(String identifier) {
        return new NotFoundException("Event with identifier " + identifier + " not found");
    }
}
