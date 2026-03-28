package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FindEventByIdUseCase {

    private final EventPort port;

    public Event execute(UUID id) {
        return port.getByIdOrThrow(id);
    }
}
