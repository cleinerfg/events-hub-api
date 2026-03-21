package com.eventshub.shared.infra.web.exception.support;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemDetailFactory {

    private final AppErrorHttpTranslator translator;

    public ProblemDetail create(AppError error, String message) {
        HttpStatus status = translator.lookup(error);

        if (status == null) {
            return handleUnmappedError(error);
        }

        return buildProblemDetail(error, message, status);
    }

    private ProblemDetail handleUnmappedError(AppError orphanError) {
        var systemIntegrity = AppException.systemIntegrity(
                "AppError '%s' [scope='%s'] is unmapped.".formatted(orphanError.getCode(), orphanError.getScope())
        );
        var status = translator.lookup(systemIntegrity.getError());
        return buildProblemDetail(systemIntegrity.getError(), systemIntegrity.getMessage(), status);
    }

    private ProblemDetail buildProblemDetail(AppError error, String message, HttpStatus status) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(error.getCode());
        problemDetail.setProperty("category", error.getCategory());
        return problemDetail;
    }
}
