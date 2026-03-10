package com.eventshub.event.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventJpaEntity, Long> {

    boolean existsByIdentifier(String identifier);

    Optional<EventJpaEntity> findByIdentifier(String identifier);
}
