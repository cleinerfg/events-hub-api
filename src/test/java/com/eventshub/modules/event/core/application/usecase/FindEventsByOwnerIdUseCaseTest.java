package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.dto.EventSummary;
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
class FindEventsByOwnerIdUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private FindAllEventsByOwnerIdUseCase sut;

    @Test
    @DisplayName("Should return a list of event summaries for a given owner id")
    void shouldReturnEventSummariesWhenOwnerHasEvents() {
        UUID ownerId = UUID.randomUUID();
        List<EventSummary> expectedEvents = List.of(
                new EventSummary(UUID.randomUUID(), "Java TEC"),
                new EventSummary(UUID.randomUUID(), "Workshop Spring Boot")
        );

        when(port.findAllEventsByOwnerId(ownerId)).thenReturn(expectedEvents);

        List<EventSummary> result = sut.execute(ownerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedEvents, result);

        verify(port).findAllEventsByOwnerId(ownerId);
    }

    @Test
    @DisplayName("Should return an empty list when owner has no events")
    void shouldReturnEmptyListWhenNoEventsFound() {
        UUID ownerId = UUID.randomUUID();
        when(port.findAllEventsByOwnerId(ownerId)).thenReturn(List.of());

        List<EventSummary> result = sut.execute(ownerId);

        assertTrue(result.isEmpty());
        verify(port).findAllEventsByOwnerId(ownerId);
    }
}