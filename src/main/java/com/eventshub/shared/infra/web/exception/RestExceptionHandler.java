package com.eventshub.shared.infra.web.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.core.exception.ErrorScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    private final Map<ErrorScope, AppErrorHttpTranslator> translators;
    private final GlobalAppErrorHttpTranslator globalTranslator;

    public RestExceptionHandler(
            List<AppErrorHttpTranslator> translatorList,
            GlobalAppErrorHttpTranslator globalTranslator
    ) {
        this.globalTranslator = globalTranslator;

        // FAIL-FAST: toUnmodifiableMap throws IllegalStateException if it has duplicate key
        this.translators = translatorList.stream()
                .collect(Collectors.toUnmodifiableMap(
                        AppErrorHttpTranslator::getScope,
                        translator -> translator
                ));
    }

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex) {
        AppError error = ex.getError();

        // O(1)
        AppErrorHttpTranslator translator = translators.getOrDefault(
                error.getScope(),
                globalTranslator
        );

        HttpStatus status = translator.translate(error);

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

        var status = globalTranslator.translate(systemIntegrity.getError());

        return buildProblemDetail(systemIntegrity.getError(), systemIntegrity.getMessage(), status);
    }
}
