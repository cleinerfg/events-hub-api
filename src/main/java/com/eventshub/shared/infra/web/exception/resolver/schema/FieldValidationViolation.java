package com.eventshub.shared.infra.web.exception.resolver.schema;

import java.util.Set;

public record FieldValidationViolation(
        String field,
        Set<String> errors
) {
}