package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FindEventByExternalIdUseCase {

    private final EventPort port;

    public Event execute(UUID externalId) {
        return port.getByExternalIdOrThrow(externalId);
    }
}
