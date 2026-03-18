package com.eventshub.modules.user.infra.web;

import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.infra.web.dto.UserResponse;
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
}
