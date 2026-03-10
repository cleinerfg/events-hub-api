package com.eventshub.event.infra.mapper;

import com.eventshub.event.core.model.Event;
import com.eventshub.event.infra.persistence.EventJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EventPersistenceMapper {

    public EventJpaEntity toEntity(Event event) {
        return EventJpaEntity.builder()
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

    public Event toDomain(EventJpaEntity jpaEntity) {
        return Event.builder()
                .id(jpaEntity.getId())
                .identifier(jpaEntity.getIdentifier())
                .name(jpaEntity.getName())
                .type(jpaEntity.getType())
                .description(jpaEntity.getDescription())
                .organizer(jpaEntity.getOrganizer())
                .location(jpaEntity.getLocation())
                .startDate(jpaEntity.getStartDate())
                .endDate(jpaEntity.getEndDate())
                .build();
    }
}
