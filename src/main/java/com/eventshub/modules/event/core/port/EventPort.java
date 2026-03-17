package com.eventshub.modules.event.core.port;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.SearchEventInput;
import com.eventshub.shared.core.exception.AppException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventPort {

    Event create(Event event);

    boolean existsByExternalId(UUID externalId);

    Optional<Event> findByExternalId(UUID externalId);

    List<Event> findAll();

    List<Event> search(SearchEventInput input);

    Event update(Event event);

    void delete(UUID externalId);

    default Event getByExternalIdOrThrow(UUID externalId) {
        return findByExternalId(externalId).orElseThrow(() -> createNotFoundException(externalId));
    }

    default AppException createNotFoundException(UUID externalId) {
        return AppException.resourceNotFound("Event", externalId);
    }
}
