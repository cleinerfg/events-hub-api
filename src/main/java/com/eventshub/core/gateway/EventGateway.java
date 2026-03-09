package com.eventshub.core.gateway;

import com.eventshub.core.domain.Event;

public interface EventGateway {

    Event create(Event event);
}
