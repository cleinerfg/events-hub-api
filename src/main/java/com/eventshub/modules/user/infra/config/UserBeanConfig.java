package com.eventshub.modules.user.infra.config;

import com.eventshub.modules.user.core.application.port.AuthPort;
import com.eventshub.modules.user.core.application.port.PasswordEncoderPort;
import com.eventshub.modules.user.core.application.port.UserPort;
import com.eventshub.modules.user.core.application.usecase.CreateUserUseCase;
import com.eventshub.modules.user.core.application.usecase.LoginUseCase;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import com.eventshub.shared.core.security.TokenPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserPort port,
            UuidGeneratorPort uuidGeneratorPort,
            PasswordEncoderPort passwordEncoderPort
    ) {
        return new CreateUserUseCase(port, uuidGeneratorPort, passwordEncoderPort);
    }

    @Bean
    public LoginUseCase loginUserUseCase(
            AuthPort authPort,
            TokenPort tokenPort
    ) {
        return new LoginUseCase(authPort, tokenPort);
    }
}
