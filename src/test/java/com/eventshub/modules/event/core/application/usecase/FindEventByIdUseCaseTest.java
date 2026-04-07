package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.shared.core.exception.GlobalAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindEventByIdUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private FindEventByIdUseCase sut;

    @Test
    @DisplayName("Should return an event when a valid ID is provided")
    void shouldReturnEventWhenIdExists() {
        UUID id = UUID.randomUUID();
        Event expected = mock(Event.class);

        when(port.findById(id)).thenReturn(Optional.of(expected));

        Event result = sut.execute(id);

        assertThat(result).isEqualTo(expected);
        verify(port).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when event is not found")
    void shouldThrowExceptionWhenEventNotFound() {
        UUID id = UUID.randomUUID();

        when(port.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.execute(id))
                .isInstanceOf(GlobalAppException.class)
                .hasMessageContaining("Event")
                .hasMessageContaining(id.toString());

        verify(port).findById(id);
    }
}