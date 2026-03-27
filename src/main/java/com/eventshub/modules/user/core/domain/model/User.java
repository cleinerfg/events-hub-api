package com.eventshub.modules.user.core.domain.model;

import com.eventshub.modules.user.core.domain.exception.InvalidEmailException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private final UUID id;
    private String name;
    private String email;
    private String passwordHash;

    private User(UUID id) {
        validateId(id);
        this.id = id;
    }

    public static User create(CreateUserProps props) {
        var user = new User(props.id());
        
        user.setName(props.name());
        user.setEmail(props.email());
        user.setPasswordHash(props.passwordHash());

        return user;
    }

    public static User reconstruct(ReconstructUserProps props) {
        return User.builder()
                .id(props.id())
                .name(props.name())
                .email(props.email())
                .passwordHash(props.passwordHash())
                .build();
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name cannot be blank");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new InvalidEmailException(email);
        }
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be blank");
        }
        this.passwordHash = passwordHash;
    }

    private static void validateId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
    }

    private static boolean isValidEmail(String email) {
        boolean isLastChar = email.endsWith("@");
        return email.contains("@") && !isLastChar;
    }
}
