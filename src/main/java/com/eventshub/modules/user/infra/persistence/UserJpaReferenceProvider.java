package com.eventshub.modules.user.infra.persistence;

import com.eventshub.shared.core.exception.GlobalAppException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserJpaReferenceProvider {
    private final UserRepository repository;
    private final EntityManager entityManager;

    public UserJpaEntity provide(UUID externalId) {
        Long id = repository.findIdByExternalId(externalId)
                .orElseThrow(() -> GlobalAppException.resourceNotFound("userId", externalId));
        return entityManager.getReference(UserJpaEntity.class, id);
    }
}
