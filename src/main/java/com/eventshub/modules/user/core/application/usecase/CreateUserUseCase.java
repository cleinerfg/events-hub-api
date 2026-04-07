package com.eventshub.modules.user.core.application.usecase;

import com.eventshub.modules.user.core.application.port.PasswordEncoderPort;
import com.eventshub.modules.user.core.application.port.UserPort;
import com.eventshub.modules.user.core.application.usecase.command.CreateUserCommand;
import com.eventshub.modules.user.core.domain.exception.EmailAlreadyExistsException;
import com.eventshub.modules.user.core.domain.model.CreateUserProps;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.modules.user.core.domain.validator.PasswordValidator;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserPort port;
    private final UuidGeneratorPort uuidGeneratorPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public User execute(CreateUserCommand command) {
        if (port.emailExists(command.email())) {
            throw new EmailAlreadyExistsException(command.email());
        }

        PasswordValidator.validate(command.password());

        var props = CreateUserProps.builder()
                .id(uuidGeneratorPort.generate())
                .name(command.name())
                .email(command.email())
                .passwordHash(passwordEncoderPort.encode(command.password()))
                .build();

        var user = User.create(props);
        return port.create(user);
    }
}
