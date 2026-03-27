package com.eventshub.modules.user.infra.web.mapper;

import com.eventshub.modules.user.core.domain.model.input.LoginInput;
import com.eventshub.modules.user.infra.web.dto.LoginRequest;
import com.eventshub.modules.user.infra.web.dto.LoginResponse;
import com.eventshub.shared.core.support.StringSanitizer;
import org.springframework.stereotype.Component;

@Component
public class LoginWebMapper {

    public LoginResponse toResponse(String token) {
        return new LoginResponse(token);
    }

    public LoginInput toInput(LoginRequest request) {
        return LoginInput.builder()
                .email(StringSanitizer.trimAndClean(request.email()).toLowerCase())
                .password(request.password())
                .build();
    }
}

