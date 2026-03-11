package com.eventshub.event.core.usecase;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.model.UpdateEventInput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateEventUseCase {

    private final EventGateway eventGateway;

    public Event execute(String identifier, UpdateEventInput eventInput) {
        Event event = eventGateway.getByIdentifierOrThrow(identifier);
        event.update(eventInput);

        return eventGateway.update(event);
    }
}
