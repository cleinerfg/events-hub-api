package com.eventshub.shared.infra.web.exception.resolver.handler;

import com.eventshub.shared.core.exception.GlobalAppException;
import com.eventshub.shared.infra.web.exception.resolver.schema.FieldFormatViolation;
import com.eventshub.shared.infra.web.exception.resolver.support.FieldFormatViolationExtractor;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InvalidQueryParamsHandler {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail handle(List<FieldError> fieldErrors) {
        var invalidParam = GlobalAppException.invalidQueryParams();
        var problemDetail = problemDetailFactory.create(
                invalidParam.getError(),
                invalidParam.getMessage()
        );

        var typeMismatchErrors = fieldErrors.stream()
                .filter(this::hasCodeTypeMismatch)
                .toList();

        if (typeMismatchErrors.isEmpty()) return null;

        var violations = typeMismatchErrors.stream()
                .map(error -> {
                    Class<?> requiredType = extractRequiredType(error);

                    return FieldFormatViolation.builder()
                            .field(error.getField())
                            .sentValue(extractSentValue(error))
                            .expectedType(extractExpectedType(requiredType))
                            .acceptedValues(extractAcceptedValues(requiredType))
                            .message(extractMessage(requiredType))
                            .build();
                })
                .toList();

        problemDetail.setProperty("fails", violations);
        return problemDetail;
    }

    private String extractSentValue(FieldError error) {
        return Objects.requireNonNull(error.getRejectedValue()).toString();
    }

    private Class<?> extractRequiredType(FieldError error) {
        return error.unwrap(TypeMismatchException.class).getRequiredType();
    }

    private Set<String> extractAcceptedValues(Class<?> requiredType) {
        return FieldFormatViolationExtractor.extractEnumConstants(requiredType);
    }

    private String extractExpectedType(Class<?> requiredType) {
        return requiredType.getSimpleName();
    }

    private String extractMessage(Class<?> requiredType) {
        return FieldFormatViolationExtractor.extractMessage(requiredType);
    }

    private boolean hasCodeTypeMismatch(FieldError error) {
        return error.getCodes() != null &&
                Arrays.asList(error.getCodes()).contains("typeMismatch");
    }

}
