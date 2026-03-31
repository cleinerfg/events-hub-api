package com.eventshub.modules.user.core.domain.validator;

import com.eventshub.modules.user.core.domain.exception.InvalidPasswordException;
import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordValidator {

    public static final int MIN_PASSWORD_LENGTH = 8;

    private static final Pattern RGX_WHITESPACE = Pattern.compile(".*\\s.*");
    private static final Pattern RGX_UPPERCASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern RGX_LOWERCASE = Pattern.compile(".*[a-z].*");
    private static final Pattern RGX_NUMBER = Pattern.compile(".*\\d.*");
    private static final Pattern RGX_SPECIAL_CHAR_IGNORE_WHITESPACE = Pattern.compile(".*[^a-zA-Z0-9\\s].*");

    public static void validate(String password) {
        CheckNotNull.forClass(PasswordValidator.class.getName())
                .field("password", password);

        Set<PasswordError> fails = new LinkedHashSet<>();

        if (password.length() < MIN_PASSWORD_LENGTH) fails.add(PasswordError.TOO_SHORT);
        if (RGX_WHITESPACE.matcher(password).matches()) fails.add(PasswordError.NO_WHITESPACE);
        if (!RGX_UPPERCASE.matcher(password).matches()) fails.add(PasswordError.MUST_HAVE_UPPERCASE);
        if (!RGX_LOWERCASE.matcher(password).matches()) fails.add(PasswordError.MUST_HAVE_LOWERCASE);
        if (!RGX_NUMBER.matcher(password).matches()) fails.add(PasswordError.MUST_HAVE_NUMBER);
        if (!RGX_SPECIAL_CHAR_IGNORE_WHITESPACE.matcher(password).matches())
            fails.add(PasswordError.MUST_HAVE_SPECIAL_CHARACTER);

        checkSequenceAndRepetition(password, fails);

        if (!fails.isEmpty()) {
            throw new InvalidPasswordException(fails);
        }
    }

    private static void checkSequenceAndRepetition(String password, Set<PasswordError> fails) {
        if (password == null || password.length() < 3) return;

        boolean foundSequence = false;
        boolean foundRepetition = false;

        for (int i = 0; i < password.length() - 2; i++) {
            char c1 = password.charAt(i);
            char c2 = password.charAt(i + 1);
            char c3 = password.charAt(i + 2);

            // Assign only if no match
            // Prevents erroneous reset to false
            if (!foundRepetition) {
                foundRepetition = checkRepetition(c1, c2, c3);
            }

            if (!foundSequence) {
                foundSequence = checkSequence(c1, c2, c3);
            }

            if (foundSequence && foundRepetition) break; // Optimization: early exit
        }

        if (foundRepetition) fails.add(PasswordError.NO_REPEATING_CHARACTER);
        if (foundSequence) fails.add(PasswordError.NO_SEQUENTIAL_CHARACTER);
    }

    private static boolean checkRepetition(char c1, char c2, char c3) {
        // Repetition (aaa, 111, DDD)
        return (c1 == c2 && c2 == c3);
    }

    private static boolean checkSequence(char c1, char c2, char c3) {
        // Identifies character sequences by char arithmetic.
        // Since characters are represented as numeric Unicode values,
        // (c1 + 1 == c2) effectively checks if characters are adjacent
        // in the ASCII/Unicode table (e.g., 'a'+1='b' or '1'+1='2').

        // Detects both ascending (123, abc) and descending (321, cba)
        boolean isAscending = (c1 + 1 == c2 && c2 + 1 == c3);
        boolean isDescending = (c1 - 1 == c2 && c2 - 1 == c3);

        return (isAscending || isDescending);
    }
}
