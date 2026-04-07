package com.eventshub.shared.core.exception.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CheckNotNullTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when field is null with default message")
    void field_ShouldThrowException_WhenValueIsNullWithDefaultMessage() {
        CheckNotNull check = CheckNotNull.forClass("TestClass");

        assertThatThrownBy(() -> check.field("myField", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("myField is required in TestClass");
    }
}
