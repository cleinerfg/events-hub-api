package com.eventshub.shared.infra.web.exception;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final List<AppErrorHttpTranslator> appErrorHttpTranslators;

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex) {
        return buildProblemDetail(ex.getError(), ex.getMessage());
    }

    private ProblemDetail buildProblemDetail(AppError error, String message) {
        var status = appErrorHttpTranslators.stream()
                .map(translator -> translator.translate(error))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        var problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(error.getCode());
        problemDetail.setProperty("category", error.getCategory());
        return problemDetail;
    }
}
