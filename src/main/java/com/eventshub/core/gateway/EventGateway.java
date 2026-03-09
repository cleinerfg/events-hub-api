package com.eventshub.core.gateway;

import com.eventshub.core.domain.Event;

import java.util.List;

public interface EventGateway {

    Event create(Event event);

    List<Event> findAll();
}
