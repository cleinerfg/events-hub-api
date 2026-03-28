package com.eventshub.modules.user.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    @Query("SELECT u.id FROM UserJpaEntity u WHERE u.externalId = :externalId")
    Optional<Long> findIdByExternalId(UUID externalId);

    boolean existsByEmail(String email);

    Optional<UserJpaEntity> findByEmail(String email);
}
