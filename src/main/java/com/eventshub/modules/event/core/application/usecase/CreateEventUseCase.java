package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.command.CreateEventCommand;
import com.eventshub.modules.event.core.domain.model.CreateEventProps;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateEventUseCase {

    private final EventPort port;
    private final UuidGeneratorPort uuidGeneratorPort;

    public Event execute(CreateEventCommand command) {
        var props = CreateEventProps.builder()
                .id(uuidGeneratorPort.generate())
                .ownerId(command.ownerId())
                .name(command.name())
                .type(command.type())
                .description(command.description())
                .organizer(command.organizer())
                .location(command.location())
                .startDate(command.startDate())
                .endDate(command.endDate())
                .build();

        var event = Event.create(props);
        return port.create(event);
    }
}
