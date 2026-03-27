package com.eventshub.modules.user.infra.persistence;

import com.eventshub.modules.user.core.application.port.UserPort;
import com.eventshub.modules.user.core.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository repository;
    private final UserPersistenceMapper mapper;

    @Override
    public User create(User user) {
        var entity = mapper.toEntity(user);

        return mapper.toDomain(
                repository.save(entity)
        );
    }

    @Override
    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }
}
