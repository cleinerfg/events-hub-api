package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.port.EventPort;
import com.eventshub.shared.core.exception.AppException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEventUseCase {

    private final EventPort port;

    public Event execute(Event event) {
        if (port.existsByIdentifier(event.getIdentifier())) {
            throw AppException.duplicateIdentifier("Event", event.getIdentifier());
        }
        return port.create(event);
    }
}
