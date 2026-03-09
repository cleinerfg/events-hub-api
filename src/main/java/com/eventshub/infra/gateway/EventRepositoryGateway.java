package com.eventshub.infra.gateway;

import com.eventshub.core.domain.Event;
import com.eventshub.core.gateway.EventGateway;
import com.eventshub.infra.mapper.EventEntityMapper;
import com.eventshub.infra.persistence.EventEntity;
import com.eventshub.infra.persistence.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class EventRepositoryGateway implements EventGateway {

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
    public List<Event> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

}
