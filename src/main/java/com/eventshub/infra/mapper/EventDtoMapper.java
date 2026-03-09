package com.eventshub.infra.mapper;

import com.eventshub.core.domain.Event;
import com.eventshub.infra.dto.EventRequest;
import com.eventshub.infra.dto.EventResponse;
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
                .name(event.name())
                .identifier(event.identifier())
                .type(event.type())
                .description(event.description())
                .organizer(event.organizer())
                .location(event.location())
                .startDate(event.startDate())
                .endDate(event.endDate())
                .build();
    }
}
