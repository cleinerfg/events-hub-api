package com.eventshub.event.core.usecase;

import com.eventshub.event.core.gateway.EventGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteEventUseCase {

    private final EventGateway eventGateway;

    public void execute(String identifier) {

        if (!eventGateway.existsByIdentifier(identifier)) {
            throw eventGateway.createNotFoundException(identifier);
        }

        eventGateway.delete(identifier);
    }
}
