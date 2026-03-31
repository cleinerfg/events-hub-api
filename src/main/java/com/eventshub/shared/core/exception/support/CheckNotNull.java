package com.eventshub.shared.core.exception.support;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckNotNull {

    private final String className;

    public static CheckNotNull forClass(String className) {
        return new CheckNotNull(className);
    }

    public CheckNotNull field(String name, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is required in " + className);
        }
        return this;
    }
}
