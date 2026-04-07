package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllEventsUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private FindAllEventsUseCase sut;

    @Test
    @DisplayName("Should return a list of events when they exist")
    void shouldReturnListOfEvents() {
        List<Event> expected = List.of(mock(Event.class), mock(Event.class));
        when(port.findAll()).thenReturn(expected);

        List<Event> result = sut.execute();

        assertThat(result)
                .isNotEmpty()
                .hasSize(2)
                .containsAll(expected);

        verify(port).findAll();
    }

    @Test
    @DisplayName("Should return an empty list when no events are found")
    void shouldReturnEmptyListWhenNoEventsExist() {
        when(port.findAll()).thenReturn(Collections.emptyList());

        List<Event> result = sut.execute();

        assertThat(result).isEmpty();
        verify(port).findAll();
    }
}