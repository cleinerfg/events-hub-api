package com.eventshub.modules.user.core.domain.model;

import com.eventshub.modules.user.core.domain.exception.InvalidEmailException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class User {

    private final UUID externalId;
    private String name;
    private String email;
    private String passwordHash;

    public static User create(CreateUserProps props) {
        validate(props);

        return reconstruct(
                props.id(),
                props.name(),
                props.email().toLowerCase(),
                props.passwordHash()
        );
    }

    public static User reconstruct(
            UUID id,
            String name,
            String email,
            String passwordHash
    ) {
        return User.builder()
                .externalId(id)
                .name(name)
                .email(email)
                .passwordHash(passwordHash)
                .build();
    }

    private static void validate(CreateUserProps props) {

        if (props.id() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (props.name() == null || props.name().isBlank()) {
            throw new IllegalArgumentException("User name cannot be blank");
        }

        if (props.passwordHash() == null || props.passwordHash().isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be blank");
        }

        if (props.email() == null || !isValidEmail(props.email())) {
            throw new InvalidEmailException(props.email());
        }
    }

    private static boolean isValidEmail(String email) {
        boolean isLastChar = email.endsWith("@");
        return email.contains("@") && !isLastChar;
    }
}
