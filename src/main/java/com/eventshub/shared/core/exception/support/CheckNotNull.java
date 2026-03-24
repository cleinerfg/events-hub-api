package com.eventshub.shared.core.exception.support;

import com.eventshub.shared.core.exception.GlobalAppException;
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
            throw GlobalAppException.systemIntegrity(
                    String.format("%s is required in %s", name, className)
            );
        }
        return this;
    }
}
