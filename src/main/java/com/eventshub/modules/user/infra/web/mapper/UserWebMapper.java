package com.eventshub.modules.user.infra.web.mapper;

import com.eventshub.modules.user.core.application.usecase.command.CreateUserCommand;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.modules.user.infra.web.dto.CreateUserRequest;
import com.eventshub.modules.user.infra.web.dto.UserResponse;
import com.eventshub.shared.core.support.StringSanitizer;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public CreateUserCommand toCreateCommand(CreateUserRequest request) {
        return CreateUserCommand.builder()
                .name(StringSanitizer.capitalize(request.name()))
                .email(StringSanitizer.trimAndClean(request.email()).toLowerCase())
                .password(request.password())
                .build();
    }
}
