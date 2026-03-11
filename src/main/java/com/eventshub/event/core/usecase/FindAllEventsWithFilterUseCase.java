package com.eventshub.event.core.usecase;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.model.FilterEventInput;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllEventsWithFilterUseCase {

    private final EventGateway eventGateway;

    public List<Event> execute(FilterEventInput filter) {
        return eventGateway.findAllWithFilter(filter);
    }
}
