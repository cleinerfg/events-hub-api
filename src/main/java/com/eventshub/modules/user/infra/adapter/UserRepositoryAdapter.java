package com.eventshub.modules.user.infra.adapter;

import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.core.port.UserPort;
import com.eventshub.modules.user.infra.persistence.UserJpaEntity;
import com.eventshub.modules.user.infra.persistence.UserPersistenceMapper;
import com.eventshub.modules.user.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserPort {

    private final UserRepository repository;
    private final UserPersistenceMapper persistenceMapper;

    @Override
    public User create(User user) {
        UserJpaEntity savedUser = repository.save(
                persistenceMapper.toJpaEntity(user)
        );
        return persistenceMapper.toDomain(savedUser);
    }

    @Override
    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }
}
