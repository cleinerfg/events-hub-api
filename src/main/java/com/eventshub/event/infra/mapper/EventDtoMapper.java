package com.eventshub.event.infra.mapper;

import com.eventshub.event.core.model.Event;
import com.eventshub.event.infra.dto.EventRequest;
import com.eventshub.event.infra.dto.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventDtoMapper {

    public Event toDomain(EventRequest dto) {
        return Event.builder()
                .identifier(dto.identifier())
                .name(dto.name())
                .type(dto.type())
                .description(dto.description())
                .organizer(dto.organizer())
                .location(dto.location())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
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
