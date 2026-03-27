package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteEventUseCase {

    private final EventPort port;

    public void execute(UUID id) {

        if (!port.existsById(id)) {
            throw port.createNotFoundException(id);
        }

        port.delete(id);
    }
}
