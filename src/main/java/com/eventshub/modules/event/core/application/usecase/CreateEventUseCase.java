package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.input.CreateEventInput;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEventUseCase {

    private final EventPort port;
    private final UuidGeneratorPort uuidGeneratorPort;

    public Event execute(CreateEventInput input) {
        var event = Event.create(input, uuidGeneratorPort.generate());
        return port.create(event);
    }
}
