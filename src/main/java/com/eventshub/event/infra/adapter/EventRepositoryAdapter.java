package com.eventshub.event.infra.adapter;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import com.eventshub.event.infra.mapper.EventPersistenceMapper;
import com.eventshub.event.infra.persistence.EventJpaEntity;
import com.eventshub.event.infra.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EventRepositoryAdapter implements EventGateway {

    private final EventRepository eventRepository;
    private final EventPersistenceMapper eventPersistenceMapper;

    @Override
    public Event create(Event event) {
        EventJpaEntity savedEvent = eventRepository.save(
                eventPersistenceMapper.toEntity(event)
        );
        return eventPersistenceMapper.toDomain(savedEvent);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return eventRepository.existsByIdentifier(identifier);
    }

    @Override
    public Optional<Event> findByIdentifier(String identifier) {
        return eventRepository.findByIdentifier(identifier)
                .map(eventPersistenceMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(eventPersistenceMapper::toDomain)
                .toList();
    }

}
