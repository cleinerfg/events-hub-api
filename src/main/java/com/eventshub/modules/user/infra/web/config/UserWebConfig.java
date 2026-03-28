package com.eventshub.modules.user.infra.web.config;

import com.eventshub.modules.user.core.domain.exception.UserError;
import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import com.eventshub.shared.infra.web.exception.support.HttpErrorSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Configuration
public class UserWebConfig {

    @Bean
    public HttpErrorSchema userHttpErrorSchema() {
        Map<AppError, HttpStatus> map = Map.of(
                UserError.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT,
                UserError.INVALID_EMAIL, HttpStatus.BAD_REQUEST,
                UserError.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED,
                UserError.INVALID_PASSWORD, HttpStatus.UNPROCESSABLE_CONTENT
        );

        return HttpErrorSchema.builder()
                .scope(ErrorScope.USER)
                .definitions(map)
                .build();

    }
}
