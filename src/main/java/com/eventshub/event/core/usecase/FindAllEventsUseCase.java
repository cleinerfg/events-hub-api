package com.eventshub.event.core.usecases;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllEventsUseCase implements FindAllEventsUseCase {

    private final EventGateway eventGateway;

    @Override
    public List<Event> execute() {
        return eventGateway.findAll();
    }
}
