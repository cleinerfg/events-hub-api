package com.eventshub.modules.user.infra.persistence;

import com.eventshub.modules.user.core.domain.model.ReconstructUserProps;
import com.eventshub.modules.user.core.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .externalId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .build();
    }

    public User toDomain(UserJpaEntity entity) {
        var props = ReconstructUserProps.builder()
                .id(entity.getExternalId())
                .name(entity.getName())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .build();

        return User.reconstruct(props);
    }
}
