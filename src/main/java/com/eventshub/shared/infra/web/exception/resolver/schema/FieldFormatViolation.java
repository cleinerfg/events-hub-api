package com.eventshub.shared.infra.web.exception.resolver.schema;

import lombok.Builder;

import java.util.Set;

@Builder
public record FieldFormatViolation(
        String fieldName,
        String sentValue,
        String expectedType,
        Set<String> acceptedValues,
        String message
) {
}
