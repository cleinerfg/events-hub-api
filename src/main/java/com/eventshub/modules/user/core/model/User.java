package com.eventshub.modules.user.core.model;

import com.eventshub.modules.user.core.model.input.CreateUserInput;
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
        return User.builder()
                .externalId(externalId)
                .name(input.name())
                .email(input.email().toLowerCase())
                .passwordHash(passwordHash)
                .build();
    }
}
