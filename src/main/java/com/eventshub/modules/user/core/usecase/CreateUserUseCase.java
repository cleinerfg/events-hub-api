package com.eventshub.modules.user.core.usecase;

import com.eventshub.modules.user.core.exception.EmailAlreadyExistsException;
import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.core.model.input.CreateUserInput;
import com.eventshub.modules.user.core.port.PasswordEncoderPort;
import com.eventshub.modules.user.core.port.UserPort;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserPort port;
    private final UuidGeneratorPort uuidGeneratorPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public User execute(CreateUserInput input) {
        if (port.emailExists(input.email())) {
            throw new EmailAlreadyExistsException(input.email());
        }

        var user = User.create(
                input,
                uuidGeneratorPort.generate(),
                passwordEncoderPort.encode(input.password())
        );
        return port.create(user);
    }
}
