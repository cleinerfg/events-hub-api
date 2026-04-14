package com.eventshub.modules.event.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface EventJpaRepository extends
        JpaRepository<EventJpaEntity, Long>,
        JpaSpecificationExecutor<EventJpaEntity> {

    @Query("SELECT e.id FROM EventJpaEntity e WHERE e.externalId = :externalId")
    Optional<Long> findIdByExternalId(UUID externalId);

    boolean existsByExternalId(UUID externalId);

    Optional<EventJpaEntity> findByExternalId(UUID externalId);

    void deleteByExternalId(UUID externalId);
}
