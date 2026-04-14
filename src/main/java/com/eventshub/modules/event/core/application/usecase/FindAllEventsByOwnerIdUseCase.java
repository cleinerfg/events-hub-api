package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.dto.EventSummary;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FindAllEventsByOwnerIdUseCase {

    private final EventPort port;

    public List<EventSummary> execute(UUID id) {
        return port.findAllEventsByOwnerId(id);
    }
}
