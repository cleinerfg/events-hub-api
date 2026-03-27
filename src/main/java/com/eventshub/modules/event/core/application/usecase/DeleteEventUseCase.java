package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteEventUseCase {

    private final EventPort port;

    public void execute(UUID externalId) {

        if (!port.existsByExternalId(externalId)) {
            throw port.createNotFoundException(externalId);
        }

        port.delete(externalId);
    }
}
