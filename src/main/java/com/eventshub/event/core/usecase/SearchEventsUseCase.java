package com.eventshub.event.core.usecase;

import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.model.input.SearchEventInput;
import com.eventshub.event.core.port.EventPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SearchEventsUseCase {

    private final EventPort port;

    public List<Event> execute(SearchEventInput input) {
        return port.search(input);
    }
}
