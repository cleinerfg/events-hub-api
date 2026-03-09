package com.eventshub.core.gateway;

import com.eventshub.core.domain.Event;

import java.util.List;
import java.util.Optional;

public interface EventGateway {

    Event create(Event event);

    boolean existsByIdentifier(String identifier);

    Optional<Event> findByIdentifier(String identifier);

    List<Event> findAll();
}
