package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.command.CreateEventCommand;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.modules.event.core.domain.model.ReconstructEventProps;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEventUseCaseTest {

    @Mock
    private EventPort port;

    @Mock
    private UuidGeneratorPort uuidGeneratorPort;

    @InjectMocks
    private CreateEventUseCase sut;

    @Test
    @DisplayName("Should create event successfully when data is valid")
    void shouldCreateEventSuccessfully() {
        var eventId = UUID.randomUUID();
        var ownerId = UUID.randomUUID();
        var command = new CreateEventCommand(
                ownerId,
                "Tech Workshop",
                EventType.WORKSHOP,
                "A great workshop about DDD",
                "EventsHub Org",
                "Lisbon, Portugal",
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(1).plusHours(3)
        );

        var props = ReconstructEventProps.builder()
                .id(eventId)
                .ownerId(ownerId)
                .name(command.name())
                .type(command.type())
                .description(command.description())
                .organizer(command.organizer())
                .location(command.location())
                .startDate(command.startDate())
                .endDate(command.endDate())
                .build();

        var expectedEvent = Event.reconstruct(props);

        when(uuidGeneratorPort.generate()).thenReturn(eventId);
        when(port.create(any(Event.class))).thenReturn(expectedEvent);

        sut.execute(command);

        // Captures the Event instance created internally to ensure it correctly
        // integrates the UUID from mock before persistence.
        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(port).create(eventCaptor.capture());

        Event result = eventCaptor.getValue();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedEvent);
    }

}