package com.eventshub.modules.user.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {

    boolean existsByEmail(String email);
}
