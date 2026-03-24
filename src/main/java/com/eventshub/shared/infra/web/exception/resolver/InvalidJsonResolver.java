package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.GlobalAppException;
import com.eventshub.shared.infra.web.exception.resolver.schema.FieldFormatViolation;
import com.eventshub.shared.infra.web.exception.resolver.support.FieldFormatViolationExtractor;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import tools.jackson.databind.exc.InvalidFormatException;

@Component
@RequiredArgsConstructor
public class InvalidJsonResolver {

    public static final String VIOLATION_SYNTAX = "SYNTAX";
    public static final String VIOLATION_FORMAT = "FORMAT";

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail resolve(HttpMessageNotReadableException ex) {
        var invalidJson = GlobalAppException.invalidJson();
        var problemDetail = problemDetailFactory.create(
                invalidJson.getError(),
                invalidJson.getMessage()
        );

        var formatViolation = extractFieldFormatViolation(ex);
        String violationType = VIOLATION_SYNTAX;

        if (formatViolation != null) {
            problemDetail.setProperty("fail", formatViolation);
            violationType = VIOLATION_FORMAT;
        }

        problemDetail.setProperty("violation", violationType);

        return problemDetail;
    }

    private FieldFormatViolation extractFieldFormatViolation(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException formatException) {
            Class<?> type = formatException.getTargetType();
            String field = formatException.getPath().isEmpty() ? "UNKNOWN_FIELD"
                    : formatException.getPath().get(0).getPropertyName();

            return FieldFormatViolation.builder()
                    .field(field)
                    .sentValue(formatException.getValue().toString())
                    .expectedType(type.getSimpleName())
                    .acceptedValues(FieldFormatViolationExtractor.extractEnumConstants(type))
                    .message(FieldFormatViolationExtractor.extractMessage(type))
                    .build();
        }
        return null;
    }
}