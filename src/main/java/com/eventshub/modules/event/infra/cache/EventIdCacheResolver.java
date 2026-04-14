package com.eventshub.modules.event.infra.cache;


import com.eventshub.modules.event.infra.persistence.EventJpaRepository;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Kept separate from {@link com.eventshub.modules.event.infra.persistence.EventJpaReferenceProvider}
 * to ensure Spring's cache proxy intercepts the call correctly —
 * self-invocation within the same bean bypasses the proxy.
 */
@RequiredArgsConstructor
@Component
public class EventIdCacheResolver {

    private final EventJpaRepository repository;

    @Cacheable(value = EventCacheNames.EVENT_INTERNAL_ID, key = "#externalId")
    public Long byExternalId(UUID externalId) {
        return repository.findIdByExternalId(externalId)
                .orElseThrow(() -> GlobalAppException.resourceNotFound("eventId", externalId));
    }
}
