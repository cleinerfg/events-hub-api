package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.core.domain.dto.ParticipantEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("""
                SELECT new com.eventshub.modules.event.core.domain.dto.ParticipantEvent(u.externalId, u.name)
                FROM EventJpaEntity e
                JOIN e.participants u
                WHERE e.externalId = :externalId
            """)
    List<ParticipantEvent> findAllParticipantsByExternalId(UUID externalId);
}
