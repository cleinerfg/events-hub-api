package com.eventshub.shared.infra.config;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import com.eventshub.shared.core.exception.GlobalAppError;
import com.eventshub.shared.infra.web.exception.support.HttpErrorSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Configuration
public class GlobalWebConfig {

    @Bean
    public HttpErrorSchema globalHttpErrorSchema() {
        Map<AppError, HttpStatus> map = Map.of(
                GlobalAppError.SYSTEM_INTEGRITY_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
                GlobalAppError.INVALID_JSON, HttpStatus.BAD_REQUEST,
                GlobalAppError.VALIDATION_FAIL, HttpStatus.BAD_REQUEST,
                GlobalAppError.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND,
                GlobalAppError.INVALID_PERIOD, HttpStatus.UNPROCESSABLE_CONTENT
        );

        return HttpErrorSchema.builder()
                .scope(ErrorScope.GLOBAL)
                .definitions(map)
                .build();

    }
}
