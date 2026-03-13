package com.eventshub.modules.event.core.usecase;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindAllEventsUseCase {

    private final EventPort port;

    public List<Event> execute() {
        return port.findAll();
    }
}
