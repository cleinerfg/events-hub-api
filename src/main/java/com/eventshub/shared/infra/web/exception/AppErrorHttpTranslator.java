package com.eventshub.shared.infra.web.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import org.springframework.http.HttpStatus;

public interface AppErrorHttpTranslator {

    HttpStatus translate(AppError error);

    ErrorScope getScope();
}
