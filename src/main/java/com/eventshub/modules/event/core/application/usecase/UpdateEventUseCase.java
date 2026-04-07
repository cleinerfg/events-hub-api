package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.command.UpdateEventCommand;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateEventUseCase {

    private final EventPort port;

    public Event execute(UUID id, UpdateEventCommand command) {
        Event event = port.findById(id)
                .orElseThrow(() -> GlobalAppException.resourceNotFound("Event", id));

        if (command.name() != null) event.setName(command.name());
        if (command.type() != null) event.setType(command.type());
        if (command.description() != null) event.setDescription(command.description());
        if (command.organizer() != null) event.setOrganizer(command.organizer());
        if (command.location() != null) event.setLocation(command.location());
        if (command.startDate() != null) event.setStartDate(command.startDate());
        if (command.endDate() != null) event.setEndDate(command.endDate());

        return port.update(event);
    }
}
