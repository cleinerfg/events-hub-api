package com.eventshub.module.user.core.domain.model;

import com.eventshub.modules.user.core.domain.exception.InvalidEmailException;
import com.eventshub.modules.user.core.domain.model.CreateUserProps;
import com.eventshub.modules.user.core.domain.model.ReconstructUserProps;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.modules.user.core.domain.model.UserMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private User sut;

    @BeforeEach
    void setUp() {
        var props = new CreateUserProps(
                UUID.randomUUID(),
                "John Doe",
                "john.doe@email.com",
                "hashed_password"
        );
        sut = User.create(props);
    }

    // --- CONSTRUCTOR / CREATION ---

    @Test
    @DisplayName("Should throw exception when creating a user without ID")
    void shouldThrowExceptionWhenIdIsNull() {
        var props = CreateUserProps.builder()
                .id(null)
                .name("John")
                .email("john@email.com")
                .passwordHash("hash")
                .build();

        assertThatThrownBy(() -> User.create(props))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UserMessages.ID_REQUIRED.getMessage());
    }

    // --- RECONSTRUCT ---

    @Test
    @DisplayName("Should reconstruct user with exactly the same properties")
    void shouldReconstructWithSameState() {
        var props = new ReconstructUserProps(
                UUID.randomUUID(),
                "Jane Doe",
                "jane@email.com",
                "another_hash"
        );

        User result = User.reconstruct(props);

        assertThat(result).usingRecursiveComparison().isEqualTo(props);
    }

    // --- SETTERS ---

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Should throw exception when setting an invalid name")
    void setName_ShouldThrowException_WhenValueIsInvalid(String invalidName) {
        assertThatThrownBy(() -> sut.setName(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UserMessages.NAME_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should capitalize name and trim spaces when setting name")
    void setName_ShouldCapitalizeAndTrim() {
        String input = "  JohN DOE    MAnSY  ";
        String expected = "John Doe Mansy";

        sut.setName(input);

        assertThat(sut.getName()).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Should throw exception when email is null or blank")
    void setEmail_ShouldThrowException_WhenValueIsBlank(String invalidEmail) {
        assertThatThrownBy(() -> sut.setEmail(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UserMessages.EMAIL_REQUIRED.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "test@",
            "@domain.com",
            "test@domain",
            "test@domain.c"
    })
    @DisplayName("Should throw InvalidEmailException for malformed emails")
    void setEmail_ShouldThrowException_WhenEmailIsMalformed(String malformedEmail) {
        assertThatThrownBy(() -> sut.setEmail(malformedEmail))
                .isInstanceOf(InvalidEmailException.class);
    }

    @Test
    @DisplayName("Should transform email to lowercase and trim spaces")
    void setEmail_ShouldTransformToLowercaseAndTrim() {
        String inputEmail = "   USER@DOMAIN.COM  ";
        String expected = "user@domain.com";

        sut.setEmail(inputEmail);

        assertThat(sut.getEmail()).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Should throw exception when password hash is invalid")
    void setPasswordHash_ShouldThrowException_WhenValueIsInvalid(String invalidHash) {
        assertThatThrownBy(() -> sut.setPasswordHash(invalidHash))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(UserMessages.PASSWORD_REQUIRED.getMessage());
    }
}