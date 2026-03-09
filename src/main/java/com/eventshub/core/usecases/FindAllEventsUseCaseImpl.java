package com.eventshub.core.usecases;

import com.eventshub.core.domain.Event;
import com.eventshub.core.gateway.EventGateway;
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
