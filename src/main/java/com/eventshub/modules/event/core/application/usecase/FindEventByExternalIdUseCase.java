package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FindEventByExternalIdUseCase {

    private final EventPort port;

    public Event execute(UUID externalId) {
        return port.getByExternalIdOrThrow(externalId);
    }
}
