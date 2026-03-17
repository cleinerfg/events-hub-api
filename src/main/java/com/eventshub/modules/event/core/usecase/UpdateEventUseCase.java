package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.UpdateEventInput;
import com.eventshub.modules.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateEventUseCase {

    private final EventPort port;

    public Event execute(UUID externalId, UpdateEventInput eventInput) {
        Event event = port.getByExternalIdOrThrow(externalId);
        event.update(eventInput);

        return port.update(event);
    }
}
