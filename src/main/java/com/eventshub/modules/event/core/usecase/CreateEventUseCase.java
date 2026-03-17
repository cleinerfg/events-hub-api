package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.CreateEventInput;
import com.eventshub.modules.event.core.port.EventPort;
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
