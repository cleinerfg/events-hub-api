package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.core.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventPersistenceMapper {

    public EventJpaEntity toEntity(Event event) {
        return EventJpaEntity.builder()
                .identifier(event.getIdentifier())
                .name(event.getName())
                .type(event.getType())
                .description(event.getDescription())
                .organizer(event.getOrganizer())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
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

    public void updateJpaEntityFromDomain(EventJpaEntity jpaEntity, Event event) {
        jpaEntity.setName(event.getName());
        jpaEntity.setType(event.getType());
        jpaEntity.setDescription(event.getDescription());
        jpaEntity.setOrganizer(event.getOrganizer());
        jpaEntity.setLocation(event.getLocation());
        jpaEntity.setStartDate(event.getStartDate());
        jpaEntity.setEndDate(event.getEndDate());
    }
}
