package com.eventshub.modules.user.infra.web.mapper;

import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.core.model.input.CreateUserInput;
import com.eventshub.modules.user.infra.web.dto.CreateUserRequest;
import com.eventshub.modules.user.infra.web.dto.UserResponse;
import com.eventshub.shared.core.support.StringSanitizer;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .externalId(user.getExternalId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public CreateUserInput toCreateInput(CreateUserRequest request) {
        return CreateUserInput.builder()
                .name(StringSanitizer.capitalize(request.name()))
                .email(StringSanitizer.trimAndClean(request.email()).toLowerCase())
                .password(request.password())
                .build();
    }
}
