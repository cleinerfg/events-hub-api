package com.eventshub.core.usecases;

import com.eventshub.core.domain.Event;

import java.util.List;

public interface FindAllEventsUseCase {

    List<Event> execute();
}
