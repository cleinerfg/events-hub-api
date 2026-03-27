package com.eventshub.modules.user.core.domain.model;

import com.eventshub.modules.user.core.domain.exception.InvalidEmailException;
import com.eventshub.modules.user.core.domain.model.input.CreateUserInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class User {

    private final UUID externalId;
    private String name;
    private String email;
    private String passwordHash;

    public static User create(
            CreateUserInput input,
            UUID externalId,
            String passwordHash
    ) {

        validate(externalId, input.name(), input.email(), passwordHash);

        return User.builder()
                .externalId(externalId)
                .name(input.name())
                .email(input.email().toLowerCase())
                .passwordHash(passwordHash)
                .build();
    }

    private static void validate(
            UUID id,
            String name,
            String email,
            String passwordHash
    ) {

        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be blank");
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be blank");
        }

        if (email == null || !isValidEmail(email)) {
            throw new InvalidEmailException(email);
        }
    }

    private static boolean isValidEmail(String email) {
        boolean isLastChar = email.endsWith("@");
        return email.contains("@") && !isLastChar;
    }
}
