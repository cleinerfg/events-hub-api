package com.eventshub.modules.user.infra.persistence;

import com.eventshub.modules.user.infra.cache.UserIdCacheResolver;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserJpaReferenceProvider {

    private final UserIdCacheResolver userIdCacheResolver;
    private final EntityManager entityManager;

    public UserJpaEntity provide(UUID externalId) {
        Long id = userIdCacheResolver.byExternalId(externalId);
        return entityManager.getReference(UserJpaEntity.class, id);
    }
}
