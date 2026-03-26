package com.eventshub.modules.user.infra.cache;


import com.eventshub.modules.user.infra.persistence.UserRepository;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Kept separate from {@link com.eventshub.modules.user.infra.persistence.UserJpaReferenceProvider}
 * to ensure Spring's cache proxy intercepts the call correctly —
 * self-invocation within the same bean bypasses the proxy.
 */
@RequiredArgsConstructor
@Component
public class UserIdCacheResolver {

    private final UserRepository repository;

    @Cacheable(value = UserCacheNames.USER_INTERNAL_ID, key = "#externalId")
    public Long byExternalId(UUID externalId) {
        return repository.findIdByExternalId(externalId)
                .orElseThrow(() -> GlobalAppException.resourceNotFound("userId", externalId));
    }
}
