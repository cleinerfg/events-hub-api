package com.eventshub.modules.user.infra.persistence;

import com.eventshub.modules.user.core.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .externalId(user.getExternalId())
                .name(user.getName())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .build();
    }

    public User toDomain(UserJpaEntity jpaEntity) {
        return User.builder()
                .externalId(jpaEntity.getExternalId())
                .name(jpaEntity.getName())
                .email(jpaEntity.getEmail())
                .passwordHash(jpaEntity.getPasswordHash())
                .build();
    }
}
