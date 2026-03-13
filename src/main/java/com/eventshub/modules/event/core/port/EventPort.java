package com.eventshub.modules.event.core.port;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.SearchEventInput;
import com.eventshub.shared.core.exception.AppException;

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

    default AppException createNotFoundException(String identifier) {
        return AppException.resourceNotFound("Event", identifier);
    }
}
