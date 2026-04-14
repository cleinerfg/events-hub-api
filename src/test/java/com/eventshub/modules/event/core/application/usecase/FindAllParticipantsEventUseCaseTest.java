package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.dto.ParticipantEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllParticipantsEventUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private FindAllParticipantsEventUseCase sut;

    @Test
    @DisplayName("Should return a list of participants when the event has records")
    void shouldReturnParticipantsListWhenEventHasEntries() {
        UUID eventId = UUID.randomUUID();
        List<ParticipantEvent> expectedParticipants = List.of(
                new ParticipantEvent(UUID.randomUUID(), "João Silva"),
                new ParticipantEvent(UUID.randomUUID(), "Maria Souza")
        );

        when(port.findAllParticipants(eventId)).thenReturn(expectedParticipants);

        List<ParticipantEvent> result = sut.execute(eventId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedParticipants, result);

        verify(port).findAllParticipants(eventId);
    }

    @Test
    @DisplayName("Should return an empty list when the event has no participants")
    void shouldReturnEmptyListWhenNoParticipantsFound() {
        UUID eventId = UUID.randomUUID();
        when(port.findAllParticipants(eventId)).thenReturn(List.of());

        List<ParticipantEvent> result = sut.execute(eventId);

        assertTrue(result.isEmpty());
        verify(port).findAllParticipants(eventId);
    }
}
