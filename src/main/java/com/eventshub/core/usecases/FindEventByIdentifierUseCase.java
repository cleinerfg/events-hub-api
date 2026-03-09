package com.eventshub.core.usecases;

import com.eventshub.core.domain.Event;

public interface FindEventByIdentifierUseCase {

    Event execute(String identifier);
}
