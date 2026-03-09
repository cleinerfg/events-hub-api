package com.eventshub.core.usecases;

import com.eventshub.core.domain.Event;

public interface CreateEventUseCase {

    Event execute(Event event);
}
