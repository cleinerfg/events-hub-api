package com.eventshub.modules.user.core.application.usecase;

import com.eventshub.modules.user.core.application.port.AuthPort;
import com.eventshub.modules.user.core.application.usecase.command.LoginCommand;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.shared.core.security.TokenPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private AuthPort authPort;

    @Mock
    private TokenPort tokenPort;

    @InjectMocks
    private LoginUseCase sut;

    @Test
    @DisplayName("Should return a valid token when credentials are correct")
    void shouldReturnTokenWhenCredentialsAreValid() {
        var command = new LoginCommand(
                "user@email.com",
                "SafePass#123"
        );
        var userId = UUID.randomUUID();
        var mockUser = mock(User.class);
        var expectedToken = "valid-jwt-token";

        when(authPort.authenticate(command.email(), command.password())).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn(userId);
        when(tokenPort.generate(userId.toString())).thenReturn(expectedToken);

        String result = sut.execute(command);

        assertThat(result).isEqualTo(expectedToken);
        verify(authPort).authenticate(command.email(), command.password());
        verify(tokenPort).generate(userId.toString());
    }
}