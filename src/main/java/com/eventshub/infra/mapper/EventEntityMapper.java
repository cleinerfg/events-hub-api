package com.eventshub.infra.mapper;

import com.eventshub.core.domain.Event;
import com.eventshub.infra.persistence.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventEntityMapper {

    public EventEntity toEntity(Event event) {
        return EventEntity.builder()
                .id(event.id())
                .identifier(event.identifier())
                .name(event.name())
                .type(event.type())
                .description(event.description())
                .organizer(event.organizer())
                .location(event.location())
                .startDate(event.startDate())
                .endDate(event.endDate())
                .build();
    }

    public Event toDomain(EventEntity eventEntity) {
        return Event.builder()
                .id(eventEntity.getId())
                .identifier(eventEntity.getIdentifier())
                .name(eventEntity.getName())
                .type(eventEntity.getType())
                .description(eventEntity.getDescription())
                .organizer(eventEntity.getOrganizer())
                .location(eventEntity.getLocation())
                .startDate(eventEntity.getStartDate())
                .endDate(eventEntity.getEndDate())
                .build();
    }
}
