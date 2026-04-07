package com.eventshub.modules.user.core.application.usecase;

import com.eventshub.modules.user.core.application.port.PasswordEncoderPort;
import com.eventshub.modules.user.core.application.port.UserPort;
import com.eventshub.modules.user.core.application.usecase.command.CreateUserCommand;
import com.eventshub.modules.user.core.domain.exception.EmailAlreadyExistsException;
import com.eventshub.modules.user.core.domain.exception.InvalidPasswordException;
import com.eventshub.modules.user.core.domain.model.ReconstructUserProps;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserPort port;

    @Mock
    private UuidGeneratorPort uuidGeneratorPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private CreateUserUseCase sut;

    @Test
    @DisplayName("Should create user successfully when data is valid")
    void shouldCreateUser() {
        var command = new CreateUserCommand(
                "John Doe",
                "john@email.com",
                "SecurePass#12"
        );
        var generatedId = UUID.randomUUID();
        var passwordHash = "hashed_password";

        var props = new ReconstructUserProps(
                generatedId,
                "John Doe",
                "john@email.com",
                passwordHash
        );

        var expectedUser = User.reconstruct(props);

        when(port.emailExists(command.email())).thenReturn(false);
        when(uuidGeneratorPort.generate()).thenReturn(generatedId);
        when(passwordEncoderPort.encode(command.password())).thenReturn(passwordHash);
        when(port.create(any(User.class))).thenReturn(expectedUser);

        sut.execute(command);

        // Captures the User instance created internally to ensure it correctly
        // integrates the UUID and Hash from mocks before persistence.
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(port).create(userCaptor.capture());

        User result = userCaptor.getValue();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("Should throw InvalidPasswordException when password validation fails")
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        var command = new CreateUserCommand(
                "Name",
                "email@test.com",
                "123"
        );

        assertThatThrownBy(() -> sut.execute(command))
                .isInstanceOf(InvalidPasswordException.class);

        verify(port, never()).create(any());
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        var command = new CreateUserCommand("John", "existing@email.com", "pass");

        when(port.emailExists(command.email())).thenReturn(true);

        assertThatThrownBy(() -> sut.execute(command))
                .isInstanceOf(EmailAlreadyExistsException.class);
    }
}