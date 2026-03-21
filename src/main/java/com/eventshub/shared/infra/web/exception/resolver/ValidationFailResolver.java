package com.eventshub.shared.infra.web.exception.resolver;

import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.infra.web.exception.support.ProblemDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ValidationFailResolver {

    private final ProblemDetailFactory problemDetailFactory;

    private List<FieldValidation> extract(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toSet()
                        )
                ))
                .entrySet().stream()
                .map(entry -> new FieldValidation(entry.getKey(), entry.getValue()))
                .toList();
    }

    public ProblemDetail resolve(MethodArgumentNotValidException ex) {
        var validationFail = AppException.validationFail();

        var fieldErrors = ex.getBindingResult().getFieldErrors();
        var fieldValidations = extract(fieldErrors);

        var problemDetail = problemDetailFactory.create(
                validationFail.getError(),
                validationFail.getMessage()
        );
        problemDetail.setProperty("fails", fieldValidations);

        return problemDetail;
    }
}
