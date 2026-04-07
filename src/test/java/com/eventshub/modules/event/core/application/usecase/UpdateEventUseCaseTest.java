package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.command.UpdateEventCommand;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.modules.event.core.domain.model.ReconstructEventProps;
import com.eventshub.shared.core.exception.GlobalAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEventUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private UpdateEventUseCase sut;

    private Event createSampleEvent() {
        return Event.reconstruct(ReconstructEventProps.builder()
                .id(UUID.randomUUID())
                .ownerId(UUID.randomUUID())
                .name("Some Name")
                .type(EventType.E_SPORTS)
                .description("Desc")
                .organizer("Organizer")
                .location("Location")
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusHours(2))
                .build());
    }

    @Test
    @DisplayName("Should update all fields successfully when event exists and command is full")
    void shouldUpdateAllFieldsSuccessfully() {
        var existingEvent = createSampleEvent();

        var command = new UpdateEventCommand(
                "New Name",
                EventType.ART,
                "New Desc",
                "Org",
                "Loc",
                OffsetDateTime.now().plusHours(1),
                OffsetDateTime.now().plusDays(5)
        );

        var props = ReconstructEventProps.builder()
                .id(existingEvent.getId())
                .ownerId(existingEvent.getOwnerId())
                .name(command.name())
                .type(command.type())
                .description(command.description())
                .organizer(command.organizer())
                .location(command.location())
                .startDate(command.startDate())
                .endDate(command.endDate())
                .build();

        var expectedEvent = Event.reconstruct(props);

        when(port.findById(existingEvent.getId())).thenReturn(Optional.of(existingEvent));
        when(port.update(any(Event.class))).thenReturn(expectedEvent);

        Event result = sut.execute(existingEvent.getId(), command);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedEvent);
        verify(port).update(existingEvent);
    }

    @Test
    @DisplayName("Should not update fields that are null in the command")
    void shouldNotUpdateNullFields() {
        var existingEvent = createSampleEvent();

        var command = new UpdateEventCommand(
                null, null,
                null, null,
                null, null, null
        );

        when(port.findById(existingEvent.getId())).thenReturn(Optional.of(existingEvent));
        when(port.update(any(Event.class))).thenReturn(existingEvent);

        Event result = sut.execute(existingEvent.getId(), command);

        assertThat(result).usingRecursiveComparison().isEqualTo(existingEvent);
        verify(port).update(existingEvent);
    }

    @Test
    @DisplayName("Should throw an exception when an event when does not exists")
    void shouldThrowExceptionWhenEventDoesNotExist() {
        UUID id = UUID.randomUUID();
        GlobalAppException expectedException = GlobalAppException.resourceNotFound(
                "Event", id
        );

        when(port.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.execute(id, mock(UpdateEventCommand.class)))
                .isInstanceOf(GlobalAppException.class)
                .hasMessage(expectedException.getMessage());

        verify(port, never()).update(any());
    }
}