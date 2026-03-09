package com.eventshub.core.usecases;

import com.eventshub.core.domain.Event;
import com.eventshub.core.exception.NotFoundEventException;
import com.eventshub.core.gateway.EventGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindEventByIdentifierUseCaseImpl implements FindEventByIdentifierUseCase {

    private final EventGateway eventGateway;

    @Override
    public Event execute(String identifier) {
        return eventGateway.findByIdentifier(identifier)
                .orElseThrow(() -> new NotFoundEventException(
                        "Event with identifier " + identifier + " not found")
                );
    }
}
