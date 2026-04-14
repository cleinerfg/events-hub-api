package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.dto.ParticipantEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FindAllParticipantsEventUseCase {

    private final EventPort port;

    public List<ParticipantEvent> execute(UUID id) {
        return port.findAllParticipants(id);
    }
}
