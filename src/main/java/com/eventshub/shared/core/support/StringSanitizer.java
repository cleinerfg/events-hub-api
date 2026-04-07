package com.eventshub.shared.core.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringSanitizer {

    public static String trimAndClean(String value) {
        if (value == null) return null;
        String cleaned = value.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? null : cleaned;
    }

    public static String capitalize(String value) {
        String cleaned = trimAndClean(value);
        if (cleaned == null) return null;

        return Arrays.stream(cleaned.split(" "))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public static String toLowerCase(String value) {
        if (value == null) return null;
        String cleaned = trimAndClean(value);
        return cleaned == null ? null : cleaned.toLowerCase();
    }
}