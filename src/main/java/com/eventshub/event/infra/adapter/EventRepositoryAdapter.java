package com.eventshub.event.infra.adapter;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.model.Event;
import com.eventshub.event.infra.mapper.EventEntityMapper;
import com.eventshub.event.infra.persistence.EventEntity;
import com.eventshub.event.infra.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class EventRepositoryAdapter implements EventGateway {

    private final EventRepository eventRepository;
    private final EventEntityMapper eventEntityMapper;

    @Override
    public Event create(Event event) {
        EventEntity savedEvent = eventRepository.save(
                eventEntityMapper.toEntity(event)
        );
        return eventEntityMapper.toDomain(savedEvent);
    }

    @Override
    public boolean existsByIdentifier(String identifier) {
        return eventRepository.existsByIdentifier(identifier);
    }

    @Override
    public Optional<Event> findByIdentifier(String identifier) {
        return eventRepository.findByIdentifier(identifier)
                .map(eventEntityMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

}
