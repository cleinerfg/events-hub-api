package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.infra.web.dto.EventRequest;
import com.eventshub.modules.event.infra.web.dto.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventDtoMapper {

    public Event toDomain(EventRequest request) {
        return Event.builder()
                .identifier(request.identifier())
                .name(request.name())
                .type(request.type())
                .description(request.description())
                .organizer(request.organizer())
                .location(request.location())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .name(event.getName())
                .identifier(event.getIdentifier())
                .type(event.getType())
                .description(event.getDescription())
                .organizer(event.getOrganizer())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }
}
