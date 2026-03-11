package com.eventshub.event.core.usecase;

import com.eventshub.event.core.exception.DuplicateIdentifierException;
import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEventUseCase {

    private final EventGateway eventGateway;

    public Event execute(Event event) {
        if (eventGateway.existsByIdentifier(event.getIdentifier())) {
            throw new DuplicateIdentifierException(event.getIdentifier());
        }
        return eventGateway.create(event);
    }
}
