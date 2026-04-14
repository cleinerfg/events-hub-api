package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RemoveParticipantEventUseCase {
    private final EventPort port;

    public void execute(UUID eventId, UUID participantId) {
        Event event = port.findById(eventId)
                .orElseThrow(() -> GlobalAppException.resourceNotFound("Event", eventId));

        event.removeParticipant(participantId);
        port.update(event);
    }
}
