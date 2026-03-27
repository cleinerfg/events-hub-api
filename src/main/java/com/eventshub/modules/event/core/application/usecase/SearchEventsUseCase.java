package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.input.SearchEventInput;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SearchEventsUseCase {

    private final EventPort port;

    public List<Event> execute(SearchEventInput input) {
        return port.search(input);
    }
}
