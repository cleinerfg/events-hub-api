package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.ReconstructEventProps;
import com.eventshub.modules.user.infra.persistence.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EventPersistenceMapper {

    public EventJpaEntity toEntity(Event event, UserJpaEntity owner) {
        return EventJpaEntity.builder()
                .externalId(event.getId())
                .owner(owner)
                .name(event.getName())
                .type(event.getType())
                .description(event.getDescription())
                .organizer(event.getOrganizer())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }

    public Event toDomain(EventJpaEntity entity) {
        var props = ReconstructEventProps.builder()
                .id(entity.getExternalId())
                .ownerId(entity.getOwner().getExternalId())
                .name(entity.getName())
                .type(entity.getType())
                .description(entity.getDescription())
                .organizer(entity.getOrganizer())
                .location(entity.getLocation())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();

        return Event.reconstruct(props);
    }

    public void updateEntity(EventJpaEntity entity, Event event) {
        entity.setName(event.getName());
        entity.setType(event.getType());
        entity.setDescription(event.getDescription());
        entity.setOrganizer(event.getOrganizer());
        entity.setLocation(event.getLocation());
        entity.setStartDate(event.getStartDate());
        entity.setEndDate(event.getEndDate());
    }
}
