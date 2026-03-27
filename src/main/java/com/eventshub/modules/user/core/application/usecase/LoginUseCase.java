package com.eventshub.modules.user.core.application.usecase;

import com.eventshub.modules.user.core.application.port.AuthPort;
import com.eventshub.modules.user.core.domain.model.input.LoginInput;
import com.eventshub.shared.core.security.TokenPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthPort authPort;
    private final TokenPort tokenPort;

    public String execute(LoginInput input) {
        var user = authPort.authenticate(input.email(), input.password());
        return tokenPort.generate(user.getExternalId().toString());
    }
}
