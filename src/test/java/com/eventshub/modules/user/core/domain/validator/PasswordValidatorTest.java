package com.eventshub.modules.user.core.domain.validator;

import com.eventshub.modules.user.core.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class PasswordValidatorTest {

    @Test
    @DisplayName("Should pass when password meets all requirements")
    void shouldPassWhenPasswordIsValid() {
        String validPassword = "SafePassword#12";

        assertThatCode(() -> PasswordValidator.validate(validPassword))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw exception when password is too short")
    void shouldFailWhenPasswordIsTooShort() {
        assertThatThrownBy(() -> PasswordValidator.validate("Ab1@"))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;
                    assertThat(exception.getFails()).contains(
                            PasswordError.TOO_SHORT.toString()
                    );
                });
    }

    @Test
    @DisplayName("Should throw exception when password has whitespace")
    void shouldFailWhenPasswordHasWhitespace() {
        assertThatThrownBy(() -> PasswordValidator.validate("My Pass#23"))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;

                    // Whitespace should be ignored as special characters when returning an error
                    // So we use 'containsExactly'
                    assertThat(exception.getFails()).containsExactly(
                            PasswordError.NO_WHITESPACE.toString()
                    );
                });
    }

    @Test
    @DisplayName("Should detect missing character types (Upper, Lower, Number, Special)")
    void shouldDetectMissingRequiredCharacters() {
        assertThatThrownBy(() -> PasswordValidator.validate("onlylowercase"))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;
                    assertThat(exception.getFails()).containsExactlyInAnyOrder(
                            PasswordError.MUST_HAVE_UPPERCASE.toString(),
                            PasswordError.MUST_HAVE_NUMBER.toString(),
                            PasswordError.MUST_HAVE_SPECIAL_CHARACTER.toString()
                    );
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa", "111", "XXX", "!!!"})
    @DisplayName("Should fail when containing repeating characters (3 or more)")
    void shouldFailWhenHasRepeatingCharacters(String password) {
        assertThatThrownBy(() -> PasswordValidator.validate(password))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;
                    assertThat(exception.getFails()).contains(
                            PasswordError.NO_REPEATING_CHARACTER.toString()
                    );
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "321", "abc", "cba"})
    @DisplayName("Should fail when containing sequential characters (3 or more)")
    void shouldFailWhenHasSequentialCharacters(String password) {
        assertThatThrownBy(() -> PasswordValidator.validate(password))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;
                    assertThat(exception.getFails()).contains(
                            PasswordError.NO_SEQUENTIAL_CHARACTER.toString()
                    );
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"#$%", "()*", ":;<"})
    @DisplayName("Should not fail for symbol sequences even if they are sequential in ASCII")
    void shouldNotFailWhenSymbolSequenceIsAsciiSequential(String password) {
        assertThatThrownBy(() -> PasswordValidator.validate(password))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;
                    Set<String> fails = exception.getFails();

                    assertThat(fails).isNotEmpty();
                    assertThat(fails).doesNotContain(PasswordError.NO_SEQUENTIAL_CHARACTER.toString());
                });
    }

    @Test
    @DisplayName("Should accumulate all errors at once")
    void shouldAccumulateAllPossibleErrors() {
        assertThatThrownBy(() -> PasswordValidator.validate("mypass"))
                .isInstanceOf(InvalidPasswordException.class)
                .satisfies(ex -> {
                    var exception = (InvalidPasswordException) ex;
                    assertThat(exception.getFails()).containsExactlyInAnyOrder(
                            PasswordError.TOO_SHORT.toString(),
                            PasswordError.MUST_HAVE_UPPERCASE.toString(),
                            PasswordError.MUST_HAVE_NUMBER.toString(),
                            PasswordError.MUST_HAVE_SPECIAL_CHARACTER.toString()
                    );
                });
    }

    @Test
    @DisplayName("Should throw exception if password is null during creation")
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThatThrownBy(() -> PasswordValidator.validate(null))
                .isInstanceOf(RuntimeException.class);
    }
}