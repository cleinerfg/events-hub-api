package com.eventshub.modules.user.infra.web.exception;

import com.eventshub.modules.user.core.exception.UserError;
import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import com.eventshub.shared.infra.web.exception.AppErrorHttpTranslator;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
public class UserErrorHttpTranslator implements AppErrorHttpTranslator {

    private final ErrorScope scope = ErrorScope.USER;

    private static final Map<UserError, HttpStatus> STATUS_MAP = Map.of(
            UserError.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT
    );

    @Override
    public HttpStatus translate(AppError error) {
        return STATUS_MAP.get(error);
    }
}
