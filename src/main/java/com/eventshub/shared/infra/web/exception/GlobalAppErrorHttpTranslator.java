package com.eventshub.shared.infra.web.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import com.eventshub.shared.core.exception.GlobalAppError;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
public class GlobalAppErrorHttpTranslator implements AppErrorHttpTranslator {

    private final ErrorScope scope = ErrorScope.GLOBAL;

    private static final Map<AppError, HttpStatus> STATUS_MAP = Map.of(
            GlobalAppError.SYSTEM_INTEGRITY_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
            GlobalAppError.INVALID_JSON, HttpStatus.BAD_REQUEST,
            GlobalAppError.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND,
            GlobalAppError.INVALID_PERIOD, HttpStatus.BAD_REQUEST
    );

    @Override
    public HttpStatus translate(AppError error) {
        return STATUS_MAP.get(error);
    }
}
