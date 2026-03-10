package com.eventshub.event.core.gateway;

import com.eventshub.event.core.Event;

import java.util.List;
import java.util.Optional;

public interface EventGateway {

    Event create(Event event);

    boolean existsByIdentifier(String identifier);

    Optional<Event> findByIdentifier(String identifier);

    List<Event> findAll();
}
