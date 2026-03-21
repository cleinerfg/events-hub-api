package com.eventshub.shared.infra.web.exception.resolver;

import java.util.Set;

public record FieldValidation(
        String field,
        Set<String> errors
) {
}