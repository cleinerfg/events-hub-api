package com.eventshub.event.core.usecase;

import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.model.input.UpdateEventInput;
import com.eventshub.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateEventUseCase {

    private final EventPort port;

    public Event execute(String identifier, UpdateEventInput eventInput) {
        Event event = port.getByIdentifierOrThrow(identifier);
        event.update(eventInput);

        return port.update(event);
    }
}
