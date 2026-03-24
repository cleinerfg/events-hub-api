package com.eventshub.shared.infra.web.exception.support;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
public record HttpErrorSchema(
        ErrorScope scope,
        Map<AppError, HttpStatus> definitions
) {
}
