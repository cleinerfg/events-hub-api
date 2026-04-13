package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.modules.event.core.domain.model.ReconstructEventProps;
import com.eventshub.shared.core.exception.GlobalAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveParticipantUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private RemoveParticipantUseCase sut;

    private final UUID participantId = UUID.randomUUID();

    private Event createSampleEvent() {
        return Event.reconstruct(ReconstructEventProps.builder()
                .id(UUID.randomUUID())
                .ownerId(UUID.randomUUID())
                .name("Event Name")
                .type(EventType.E_SPORTS)
                .description("Desc")
                .organizer("Organizer")
                .location("Location")
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusHours(2))
                .participantIds(Set.of(participantId))
                .build());
    }

    @Test
    @DisplayName("Should remove participant successfully when event exists and participant ID is valid")
    void shouldRemoveParticipantSuccessfully() {
        var event = createSampleEvent();

        when(port.findById(event.getId())).thenReturn(Optional.of(event));

        sut.execute(event.getId(), participantId);

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(port).update(eventCaptor.capture());

        Event updatedEvent = eventCaptor.getValue();
        assertThat(updatedEvent.getParticipantIds()).isNotNull();
        assertThat(updatedEvent.getParticipantIds()).doesNotContain(participantId);
        verify(port).findById(event.getId());
    }

    @Test
    @DisplayName("Should throw an exception when event does not exist")
    void shouldThrowExceptionWhenEventDoesNotExist() {
        var eventId = UUID.randomUUID();
        when(port.findById(eventId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.execute(eventId, participantId))
                .isInstanceOf(GlobalAppException.class)
                .hasMessageContaining("Event")
                .hasMessageContaining(eventId.toString());

        verify(port, never()).update(any());
    }
}