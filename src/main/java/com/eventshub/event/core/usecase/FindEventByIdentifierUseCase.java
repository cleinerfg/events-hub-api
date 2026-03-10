package com.eventshub.event.core.usecases;

import com.eventshub.event.core.exception.NotFoundException;
import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindEventByIdentifierUseCase implements FindEventByIdentifierUseCase {

    private final EventGateway eventGateway;

    @Override
    public Event execute(String identifier) {
        return eventGateway.findByIdentifier(identifier)
                .orElseThrow(() -> new NotFoundException(
                        "Event with identifier " + identifier + " not found")
                );
    }
}
