package com.eventshub.modules.user.core.application.usecase;

import com.eventshub.modules.user.core.application.port.AuthPort;
import com.eventshub.modules.user.core.application.usecase.command.LoginCommand;
import com.eventshub.shared.core.security.TokenPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthPort authPort;
    private final TokenPort tokenPort;

    public String execute(LoginCommand command) {
        var user = authPort.authenticate(command.email(), command.password());
        return tokenPort.generate(user.getExternalId().toString());
    }
}
