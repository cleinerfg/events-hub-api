package com.eventshub.event.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Event {

    private Long id;
    private String identifier;
    private String name;
    private EventType type;
    private String description;
    private String organizer;
    private String location;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
}
