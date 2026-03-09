package com.eventshub.core.gateway;

import com.eventshub.core.domain.Event;

import java.util.List;

public interface EventGateway {

    Event create(Event event);

    boolean existsByIdentifier(String identifier);

    List<Event> findAll();
}
