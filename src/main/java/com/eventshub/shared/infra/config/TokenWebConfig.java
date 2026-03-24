package com.eventshub.shared.infra.config;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import com.eventshub.shared.core.security.TokenError;
import com.eventshub.shared.infra.web.exception.support.HttpErrorSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Configuration
public class TokenWebConfig {

    @Bean
    public HttpErrorSchema tokenHttpErrorSchema() {
        Map<AppError, HttpStatus> map = Map.of(
                TokenError.TOKEN_REQUIRED, HttpStatus.UNAUTHORIZED,
                TokenError.TOKEN_INVALID, HttpStatus.UNAUTHORIZED,
                TokenError.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED
        );

        return HttpErrorSchema.builder()
                .scope(ErrorScope.GLOBAL)
                .definitions(map)
                .build();

    }
}
