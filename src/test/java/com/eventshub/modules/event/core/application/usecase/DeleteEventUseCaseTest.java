package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.shared.core.exception.GlobalAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteEventUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private DeleteEventUseCase sut;

    @Test
    @DisplayName("Should delete successfully an event when it exists")
    void shouldDeleteEventWhenIdExists() {
        UUID id = UUID.randomUUID();
        when(port.existsById(id)).thenReturn(true);

        sut.execute(id);

        verify(port).existsById(id);
        verify(port).delete(id);
    }

    @Test
    @DisplayName("Should throw an exception when an event when does not exists")
    void shouldThrowExceptionWhenEventDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(port.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> sut.execute(id))
                .isInstanceOf(GlobalAppException.class)
                .hasMessageContaining("Event")
                .hasMessageContaining(id.toString());

        verify(port).existsById(id);
        verify(port, never()).delete(id);
    }
}