package com.eventshub.modules.event.core.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventMessages {

    NAME_REQUIRED("Event name cannot be blank"),
    ID_REQUIRED("Event id cannot be null"),
    OWNER_ID_REQUIRED("Event owner id cannot be null"),
    TYPE_REQUIRED("Event type cannot be null"),
    ORGANIZER_REQUIRED("Event organizer cannot be blank"),
    LOCATION_REQUIRED("Event location cannot be blank"),
    START_DATE_REQUIRED("Event start date cannot be null");

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
