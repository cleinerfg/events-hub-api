package com.eventshub.modules.user.infra.auth;

import com.eventshub.modules.user.core.application.port.AuthPort;
import com.eventshub.modules.user.core.domain.exception.InvalidCredentialsException;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.modules.user.infra.persistence.UserJpaEntity;
import com.eventshub.modules.user.infra.persistence.UserPersistenceMapper;
import com.eventshub.shared.core.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthPort {

    private final AuthenticationManager authenticationManager;
    private final UserPersistenceMapper persistenceMapper;

    @Override
    public User authenticate(String email, String password) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(email, password);
            var auth = authenticationManager.authenticate(authToken);

            if (auth.getPrincipal() instanceof UserJpaEntity jpaEntity) {
                return persistenceMapper.toDomain(jpaEntity);
            }

            throw GlobalAppException.systemIntegrity(
                    "Unexpected principal type returned from authentication manager."
            );

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }
}
