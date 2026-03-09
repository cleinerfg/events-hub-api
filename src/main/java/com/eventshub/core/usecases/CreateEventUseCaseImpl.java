package com.eventshub.core.usecases;

import com.eventshub.core.domain.Event;
import com.eventshub.core.exception.DuplicateEventIdentifierException;
import com.eventshub.core.gateway.EventGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventGateway eventGateway;

    @Override
    public Event execute(Event event) {
        if (eventGateway.existsByIdentifier(event.identifier())) {
            throw new DuplicateEventIdentifierException(event.identifier());
        }
        return eventGateway.create(event);
    }
}
