package com.eventshub.modules.user.infra.config;

import com.eventshub.modules.user.core.port.PasswordEncoderPort;
import com.eventshub.modules.user.core.port.UserPort;
import com.eventshub.modules.user.core.usecase.CreateUserUseCase;
import com.eventshub.shared.core.port.UuidGeneratorPort;
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
}
