package com.eventshub.modules.user.core.application.usecase;

import com.eventshub.modules.user.core.application.port.PasswordEncoderPort;
import com.eventshub.modules.user.core.application.port.UserPort;
import com.eventshub.modules.user.core.domain.exception.EmailAlreadyExistsException;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.modules.user.core.domain.model.input.CreateUserInput;
import com.eventshub.modules.user.core.domain.validator.PasswordValidator;
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

        PasswordValidator.create(input.password())
                .validate();

        var user = User.create(
                input,
                uuidGeneratorPort.generate(),
                passwordEncoderPort.encode(input.password())
        );
        return port.create(user);
    }
}
