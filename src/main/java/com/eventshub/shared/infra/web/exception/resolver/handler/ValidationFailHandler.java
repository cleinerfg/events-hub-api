package com.eventshub.shared.infra.web.exception.resolver.handler;

import com.eventshub.shared.core.exception.GlobalAppException;
import com.eventshub.shared.infra.web.exception.resolver.schema.FieldValidationViolation;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationFailHandler {

    private final ProblemDetailFactory problemDetailFactory;

    public ProblemDetail handle(List<FieldError> fieldErrors) {
        var validationFail = GlobalAppException.validationFail();
        var problemDetail = problemDetailFactory.create(
                validationFail.getError(),
                validationFail.getMessage()
        );

        problemDetail.setProperty("fails", extractViolations(fieldErrors));

        return problemDetail;
    }

    private List<FieldValidationViolation> extractViolations(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toSet()
                        )
                ))
                .entrySet().stream()
                .map(entry -> new FieldValidationViolation(entry.getKey(), entry.getValue()))
                .toList();
    }
}
