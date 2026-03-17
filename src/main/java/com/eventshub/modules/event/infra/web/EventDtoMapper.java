package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.infra.web.dto.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventDtoMapper {

    public EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .name(event.getName())
                .externalId(event.getExternalId())
                .type(event.getType())
                .description(event.getDescription())
                .organizer(event.getOrganizer())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }
}
