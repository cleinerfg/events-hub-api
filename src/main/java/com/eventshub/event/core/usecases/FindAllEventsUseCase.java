package com.eventshub.event.core.usecases;

import com.eventshub.event.core.Event;
import com.eventshub.event.core.gateway.EventGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllEventsUseCaseImpl implements FindAllEventsUseCase {

    private final EventGateway eventGateway;

    @Override
    public List<Event> execute() {
        return eventGateway.findAll();
    }
}
