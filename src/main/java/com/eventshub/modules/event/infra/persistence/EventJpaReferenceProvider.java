package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.infra.cache.EventIdCacheResolver;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventJpaReferenceProvider {

    private final EventIdCacheResolver eventIdCacheResolver;
    private final EntityManager entityManager;

    public EventJpaEntity provide(UUID externalId) {
        Long id = eventIdCacheResolver.byExternalId(externalId);
        return entityManager.getReference(EventJpaEntity.class, id);
    }
}
