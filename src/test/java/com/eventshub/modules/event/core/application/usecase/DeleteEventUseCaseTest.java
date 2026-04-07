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
        UUID eventId = UUID.randomUUID();
        when(port.existsById(eventId)).thenReturn(true);

        sut.execute(eventId);

        verify(port).existsById(eventId);
        verify(port).delete(eventId);
    }

    @Test
    @DisplayName("Should throw an exception when an event when does not exists")
    void shouldThrowExceptionWhenEventDoesNotExist() {
        UUID eventId = UUID.randomUUID();
        GlobalAppException expectedException = GlobalAppException.resourceNotFound(
                "Event", eventId
        );

        when(port.existsById(eventId)).thenReturn(false);

        assertThatThrownBy(() -> sut.execute(eventId))
                .isInstanceOf(GlobalAppException.class)
                .hasMessage(expectedException.getMessage());

        verify(port).existsById(eventId);
        verify(port, never()).delete(eventId);
    }
}