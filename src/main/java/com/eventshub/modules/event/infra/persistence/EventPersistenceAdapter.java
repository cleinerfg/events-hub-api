package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.SearchEventInput;
import com.eventshub.modules.event.core.port.EventPort;
import com.eventshub.modules.user.infra.persistence.UserJpaEntity;
import com.eventshub.modules.user.infra.persistence.UserJpaReferenceProvider;
import com.eventshub.shared.core.exception.GlobalAppException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class EventPersistenceAdapter implements EventPort {

    private final EventJpaRepository repository;
    private final EventPersistenceMapper persistenceMapper;

    private final UserJpaReferenceProvider userJpaReferenceProvider;

    @Override
    public Event create(Event event) {
        UserJpaEntity owner = userJpaReferenceProvider
                .provide(event.getOwnerExternalId());

        var eventEntity = persistenceMapper.toEntity(event, owner);

        return persistenceMapper.toDomain(repository.save(eventEntity));
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
        EventJpaEntity jpaEntity = repository.findByExternalId(event.getExternalId()).orElseThrow(
                () -> GlobalAppException.systemIntegrity(
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
