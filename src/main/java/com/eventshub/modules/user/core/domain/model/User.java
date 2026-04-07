package com.eventshub.modules.user.core.domain.model;

import com.eventshub.modules.user.core.domain.exception.InvalidEmailException;
import com.eventshub.shared.core.support.StringSanitizer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private final UUID id;
    private String name;
    private String email;
    private String passwordHash;

    private static final Pattern RGX_EMAIL = Pattern.compile(
            "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

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
        String sanitized = StringSanitizer.capitalize(name);
        if (sanitized == null || sanitized.isBlank()) {
            throw new IllegalArgumentException(UserMessages.NAME_REQUIRED.getMessage());
        }

        this.name = sanitized;
    }

    public void setEmail(String email) {
        String sanitized = StringSanitizer.toLowerCase(email);
        if (sanitized == null || sanitized.isBlank()) {
            throw new IllegalArgumentException(UserMessages.EMAIL_REQUIRED.getMessage());
        }

        if (!isValidEmail(sanitized)) throw new InvalidEmailException(sanitized);

        this.email = sanitized;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException(UserMessages.PASSWORD_REQUIRED.getMessage());
        }
        this.passwordHash = passwordHash;
    }

    private static void validateId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(UserMessages.ID_REQUIRED.getMessage());
        }
    }

    private static boolean isValidEmail(String email) {
        return RGX_EMAIL.matcher(email).matches();
    }
}
