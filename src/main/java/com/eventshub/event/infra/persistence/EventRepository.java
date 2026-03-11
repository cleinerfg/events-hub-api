package com.eventshub.event.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EventRepository extends
        JpaRepository<EventJpaEntity, Long>,
        JpaSpecificationExecutor<EventJpaEntity> {

    boolean existsByIdentifier(String identifier);

    Optional<EventJpaEntity> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}
