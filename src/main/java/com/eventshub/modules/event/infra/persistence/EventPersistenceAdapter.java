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
    private final EventPersistenceMapper mapper;

    private final UserJpaReferenceProvider userJpaReferenceProvider;

    @Override
    public Event create(Event event) {
        UserJpaEntity owner = userJpaReferenceProvider
                .provide(event.getOwnerExternalId());

        var entity = mapper.toEntity(event, owner);

        return mapper.toDomain(
                repository.save(entity)
        );
    }

    @Override
    public boolean existsByExternalId(UUID externalId) {
        return repository.existsByExternalId(externalId);
    }

    @Override
    public Optional<Event> findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId)
                .map(mapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> search(SearchEventInput input) {

        var spec = EventSpecs.from(input);

        return repository.findAll(spec)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Event update(Event event) {
        EventJpaEntity entity = repository.findByExternalId(event.getExternalId()).orElseThrow(
                () -> GlobalAppException.systemIntegrity(
                        "Event with externalId '%s' lost during update".formatted(event.getExternalId())
                ));

        mapper.updateEntity(entity, event);

        return mapper.toDomain(
                repository.save(entity)
        );
    }

    @Override
    @Transactional
    public void delete(UUID externalId) {
        repository.deleteByExternalId(externalId);
    }
}
