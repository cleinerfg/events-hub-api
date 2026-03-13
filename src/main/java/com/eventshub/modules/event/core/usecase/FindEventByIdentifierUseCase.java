package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindEventByIdentifierUseCase {

    private final EventPort port;

    public Event execute(String identifier) {
        return port.getByIdentifierOrThrow(identifier);
    }
}
