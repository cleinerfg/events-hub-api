package com.eventshub.modules.user.core.validator;

import com.eventshub.modules.user.core.exception.InvalidPasswordException;
import com.eventshub.shared.core.exception.support.CheckNotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordValidator {

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final String TOO_SHORT = "TOO_SHORT";
    public static final String MUST_HAVE_UPPERCASE = "MUST_HAVE_UPPERCASE";
    public static final String MUST_HAVE_LOWERCASE = "MUST_HAVE_LOWERCASE";
    public static final String MUST_HAVE_NUMBER = "MUST_HAVE_NUMBER";
    public static final String MUST_HAVE_SPECIAL_CHARACTER = "MUST_HAVE_SPECIAL_CHARACTER";
    public static final String NO_SEQUENTIAL_CHARACTER = "NO_SEQUENTIAL_CHARACTER";
    public static final String NO_REPEATING_CHARACTER = "NO_REPEATING_CHARACTER";

    private final String password;
    private final Set<String> fails = new LinkedHashSet<>();

    public static PasswordValidator create(String password) {
        CheckNotNull.forClass(PasswordValidator.class.getName())
                .field("password", password);

        return new PasswordValidator(password);
    }

    public void validate() {
        if (password.length() < MIN_PASSWORD_LENGTH) fails.add(TOO_SHORT);
        if (!password.matches(".*[A-Z].*")) fails.add(MUST_HAVE_UPPERCASE);
        if (!password.matches(".*[a-z].*")) fails.add(MUST_HAVE_LOWERCASE);
        if (!password.matches(".*\\d.*")) fails.add(MUST_HAVE_NUMBER);
        if (!password.matches(".*[^a-zA-Z0-9].*")) fails.add(MUST_HAVE_SPECIAL_CHARACTER);

        checkSequenceAndRepetition();

        if (!fails.isEmpty()) {
            throw new InvalidPasswordException(fails);
        }
    }

    private void checkSequenceAndRepetition() {
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

        if (foundRepetition) fails.add(NO_REPEATING_CHARACTER);
        if (foundSequence) fails.add(NO_SEQUENTIAL_CHARACTER);
    }

    private boolean checkRepetition(char c1, char c2, char c3) {
        // Repetition (aaa, 111, DDD)
        return (c1 == c2 && c2 == c3);
    }

    private boolean checkSequence(char c1, char c2, char c3) {

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
