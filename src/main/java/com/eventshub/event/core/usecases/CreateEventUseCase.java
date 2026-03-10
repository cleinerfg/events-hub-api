package com.eventshub.event.core.usecases;

import com.eventshub.event.core.Event;
import com.eventshub.event.core.exception.DuplicateIdentifierException;
import com.eventshub.event.core.gateway.EventGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEventUseCase implements CreateEventUseCase {

    private final EventGateway eventGateway;

    @Override
    public Event execute(Event event) {
        if (eventGateway.existsByIdentifier(event.identifier())) {
            throw new DuplicateIdentifierException(event.identifier());
        }
        return eventGateway.create(event);
    }
}
