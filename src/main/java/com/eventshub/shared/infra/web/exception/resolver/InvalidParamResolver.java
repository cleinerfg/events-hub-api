package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.GlobalAppException;
import com.eventshub.shared.infra.web.exception.resolver.schema.FieldFormatViolation;
import com.eventshub.shared.infra.web.exception.resolver.support.FieldFormatViolationExtractor;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Component
@RequiredArgsConstructor
public class InvalidParamResolver {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail resolve(MethodArgumentTypeMismatchException ex) {
        var invalidParam = GlobalAppException.invalidParam();
        var problemDetail = problemDetailFactory.create(
                invalidParam.getError(),
                invalidParam.getMessage()
        );

        var violation = extractFieldFormatViolation(ex);
        problemDetail.setProperty("fail", violation);

        return problemDetail;
    }

    private FieldFormatViolation extractFieldFormatViolation(MethodArgumentTypeMismatchException ex) {
        Class<?> type = ex.getRequiredType();

        return FieldFormatViolation.builder()
                .field(ex.getName())
                .sentValue(ex.getValue() != null ? ex.getValue().toString() : "null")
                .expectedType(type != null ? type.getSimpleName() : "UNKNOWN")
                .acceptedValues(FieldFormatViolationExtractor.extractEnumConstants(type))
                .message(FieldFormatViolationExtractor.extractMessage(type))
                .build();
    }
}
