package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.query.SearchEventQuery;
import com.eventshub.modules.event.core.domain.model.Event;
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
    private final EventJpaReferenceProvider eventJpaReferenceProvider;

    @Override
    public Event create(Event event) {
        UserJpaEntity owner = userJpaReferenceProvider
                .provide(event.getOwnerId());

        var entity = mapper.toEntity(event, owner);

        return mapper.toDomain(
                repository.save(entity)
        );
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsByExternalId(id);
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return repository.findByExternalId(id)
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
    public List<Event> search(SearchEventQuery query) {

        var spec = EventSpecs.from(query);

        return repository.findAll(spec)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Event update(Event event) {
        EventJpaEntity entity = repository.findByExternalId(event.getId()).orElseThrow(
                () -> GlobalAppException.systemIntegrity(
                        "Event with id '%s' lost during update".formatted(event.getId())
                ));

        mapper.updateEntity(entity, event);

        return mapper.toDomain(
                repository.save(entity)
        );
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteByExternalId(id);
    }

    @Override
    @Transactional
    public void addParticipant(UUID eventId, UUID userId) {
        EventJpaEntity eventRef = eventJpaReferenceProvider.provide(eventId);
        UserJpaEntity userRef = userJpaReferenceProvider.provide(userId);

        eventRef.addParticipant(userRef);
        repository.save(eventRef);
    }

    @Override
    @Transactional
    public void removeParticipant(UUID eventId, UUID userId) {
        EventJpaEntity eventRef = eventJpaReferenceProvider.provide(eventId);
        UserJpaEntity userRef = userJpaReferenceProvider.provide(userId);

        eventRef.removeParticipant(userRef);
        repository.save(eventRef);
    }
}
