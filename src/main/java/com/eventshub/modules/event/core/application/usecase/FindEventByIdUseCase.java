package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class FindEventByIdUseCase {

    private final EventPort port;

    public Event execute(UUID id) {
        return port.findById(id)
                .orElseThrow(() -> GlobalAppException.resourceNotFound("Event", id));
    }
}
