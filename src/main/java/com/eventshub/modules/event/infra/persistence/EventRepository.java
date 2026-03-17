package com.eventshub.modules.event.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends
        JpaRepository<EventJpaEntity, Long>,
        JpaSpecificationExecutor<EventJpaEntity> {

    boolean existsByExternalId(UUID externalId);

    Optional<EventJpaEntity> findByExternalId(UUID externalId);

    void deleteByExternalId(UUID externalId);
}
