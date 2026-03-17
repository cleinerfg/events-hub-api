package com.eventshub.modules.event.infra.adapter;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.SearchEventInput;
import com.eventshub.modules.event.core.port.EventPort;
import com.eventshub.modules.event.infra.persistence.EventJpaEntity;
import com.eventshub.modules.event.infra.persistence.EventPersistenceMapper;
import com.eventshub.modules.event.infra.persistence.EventRepository;
import com.eventshub.modules.event.infra.persistence.EventSpecs;
import com.eventshub.shared.core.exception.AppException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class EventRepositoryAdapter implements EventPort {

    private final EventRepository repository;
    private final EventPersistenceMapper persistenceMapper;

    @Override
    public Event create(Event event) {
        EventJpaEntity savedEvent = repository.save(
                persistenceMapper.toEntity(event)
        );
        return persistenceMapper.toDomain(savedEvent);
    }

    @Override
    public boolean existsByExternalId(UUID externalId) {
        return repository.existsByExternalId(externalId);
    }

    @Override
    public Optional<Event> findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll()
                .stream()
                .map(persistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> search(SearchEventInput input) {

        var spec = EventSpecs.from(input);

        return repository.findAll(spec)
                .stream()
                .map(persistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Event update(Event event) {
        EventJpaEntity jpaEntity = repository.findById(event.getId()).orElseThrow(
                () -> AppException.systemIntegrity(
                        "Event with externalId '%s' lost during update".formatted(event.getExternalId())
                ));

        persistenceMapper.updateJpaEntityFromDomain(jpaEntity, event);

        EventJpaEntity updatedEntity = repository.save(jpaEntity);

        return persistenceMapper.toDomain(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID externalId) {
        repository.deleteByExternalId(externalId);
    }
}
