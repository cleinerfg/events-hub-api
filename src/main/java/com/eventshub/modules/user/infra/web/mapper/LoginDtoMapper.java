package com.eventshub.modules.user.infra.web.mapper;

import com.eventshub.modules.user.core.model.input.LoginUserInput;
import com.eventshub.modules.user.infra.web.dto.LoginRequest;
import com.eventshub.modules.user.infra.web.dto.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginDtoMapper {

    public LoginResponse toResponse(String token) {
        return new LoginResponse(token);
    }

    public LoginUserInput toInput(LoginRequest request) {
        return LoginUserInput.builder()
                .email(request.email())
                .password(request.password())
                .build();
    }
}

