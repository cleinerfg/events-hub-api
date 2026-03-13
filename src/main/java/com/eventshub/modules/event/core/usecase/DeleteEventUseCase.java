package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteEventUseCase {

    private final EventPort port;

    public void execute(String identifier) {

        if (!port.existsByIdentifier(identifier)) {
            throw port.createNotFoundException(identifier);
        }

        port.delete(identifier);
    }
}
