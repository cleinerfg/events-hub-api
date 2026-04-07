package com.eventshub.shared.core.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringSanitizerTest {

    @Test
    @DisplayName("Should normalize with clean and trim")
    void shouldNormalizeWithCleanAndTrim() {
        String input = "   Sentence   normalized  ";
        String expected = "Sentence normalized";

        assertThat(StringSanitizer.trimAndClean(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should capitalize and trim")
    void shouldCapitalizeAndTrim() {
        String input = "   SENtenCE  tRIM  anD CapitalIZED   ";
        String expected = "Sentence Trim And Capitalized";

        assertThat(StringSanitizer.capitalize(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should transform to lowercase and clean")
    void shouldNormalizeAndLowercase() {
        String input = "  SENTencE   NORMAlized   ";
        String expected = "sentence normalized";

        assertThat(StringSanitizer.toLowerCase(input)).isEqualTo(expected);
    }
}
