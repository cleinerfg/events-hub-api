package com.eventshub.shared.infra.web.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
    private final AppErrorHttpTranslator translator;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var invalidJson = AppException.invalidJson();
        var status = translator.lookup(invalidJson.getError());
        return buildProblemDetail(invalidJson.getError(), invalidJson.getMessage(), status);
    }

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex) {
        AppError error = ex.getError();

        HttpStatus status = translator.lookup(error);

        if (status == null) {
            return translatorSystemIntegrityHandler(error);
        }

        return buildProblemDetail(error, ex.getMessage(), status);
    }

    private ProblemDetail buildProblemDetail(AppError error, String message, HttpStatus status) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(error.getCode());
        problemDetail.setProperty("category", error.getCategory());
        return problemDetail;
    }

    private ProblemDetail translatorSystemIntegrityHandler(AppError orphanError) {
        log.error("[SYSTEM_INTEGRITY] AppError '{}' [scope='{}'] is unmapped.",
                orphanError.getCode(), orphanError.getScope());

        var systemIntegrity = AppException.systemIntegrity(
                "AppError '%s' [scope='%s'] is unmapped.".formatted(orphanError.getCode(), orphanError.getScope())
        );
        var status = translator.lookup(systemIntegrity.getError());
        return buildProblemDetail(systemIntegrity.getError(), systemIntegrity.getMessage(), status);
    }
}
