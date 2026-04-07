package com.eventshub.shared.core.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalAppExceptionTest {

    @Test
    @DisplayName("Should create invalid JSON exception with correct error and message")
    void shouldCreateInvalidJsonException() {
        GlobalAppException ex = GlobalAppException.invalidJson();

        assertThat(ex.getError()).isEqualTo(GlobalAppError.INVALID_JSON);
        assertThat(ex.getMessage()).isEqualTo("The provided JSON is invalid");
    }

    @Test
    @DisplayName("Should format resource not found message correctly")
    void shouldFormatResourceNotFoundMessage() {
        UUID id = UUID.randomUUID();
        String resource = "User";

        GlobalAppException ex = GlobalAppException.resourceNotFound(resource, id);

        assertThat(ex.getError()).isEqualTo(GlobalAppError.RESOURCE_NOT_FOUND);
        assertThat(ex.getMessage()).contains("User").contains(id.toString());
    }

    @Test
    @DisplayName("Should mask technical details in system integrity errors")
    void shouldMaskSystemIntegrityMessage() {
        String sensitiveInfo = "Internal Database Connection Timeout at Cluster-01";

        GlobalAppException ex = GlobalAppException.systemIntegrity(sensitiveInfo);
        
        assertThat(ex.getError()).isEqualTo(GlobalAppError.SYSTEM_INTEGRITY_ERROR);
        assertThat(ex.getMessage()).isEqualTo("System Integrity Violation");
        assertThat(ex.getMessage()).doesNotContain(sensitiveInfo);
    }

    @Test
    @DisplayName("Should create invalid period exception with formatted dates")
    void shouldCreateInvalidPeriodException() {
        String start = "2023-01-01";
        String end = "2022-01-01";

        GlobalAppException ex = GlobalAppException.invalidPeriod(start, end);

        assertThat(ex.getError()).isEqualTo(GlobalAppError.INVALID_PERIOD);
        assertThat(ex.getMessage()).contains(start).contains(end);
    }
}