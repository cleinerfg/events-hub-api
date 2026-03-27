package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.input.UpdateEventInput;
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
