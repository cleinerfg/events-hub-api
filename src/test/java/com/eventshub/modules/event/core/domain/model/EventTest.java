package com.eventshub.modules.event.core.domain.model;

import com.eventshub.shared.core.exception.GlobalAppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    private final UUID sutOwnerId = UUID.randomUUID();
    private Event sut;

    @BeforeEach
    void setUp() {
        var now = OffsetDateTime.now();
        var props = new CreateEventProps(
                UUID.randomUUID(),
                sutOwnerId,
                "Valid Event",
                EventType.WORKSHOP,
                "Description",
                "Organizer",
                "Location",
                now,
                now.plusHours(2)
        );
        sut = Event.create(props);
    }

    // --- CONSTRUCTOR / CREATION ---

    @Test
    @DisplayName("Should throw exception when creating an event without ID")
    void shouldThrowExceptionWhenIdIsNull() {
        var props = CreateEventProps.builder()
                .id(null)
                .ownerId(UUID.randomUUID())
                .build();

        assertThatThrownBy(() -> Event.create(props))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.ID_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when creating an event without Owner ID")
    void shouldThrowExceptionWhenOwnerIdIsNull() {
        var props = CreateEventProps.builder()
                .id(UUID.randomUUID())
                .ownerId(null)
                .build();

        assertThatThrownBy(() -> Event.create(props))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.OWNER_ID_REQUIRED.getMessage());
    }

    // --- RECONSTRUCT ---

    @Test
    @DisplayName("Should reconstruct event with exactly the same properties")
    void shouldReconstructWithSameState() {
        var participantIds = Set.of(UUID.randomUUID(), UUID.randomUUID());
        var props = new ReconstructEventProps(
                UUID.randomUUID(), UUID.randomUUID(),
                "Recon", EventType.FASHION,
                "Desc", "Org",
                "Local", OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(1),
                participantIds

        );

        Event result = Event.reconstruct(props);

        assertThat(result).usingRecursiveComparison().isEqualTo(props);
    }

    // --- SETTERS ---

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Should throw exception when setting an invalid name")
    void setName_ShouldThrowException_WhenValueIsInvalid(String invalidName) {
        assertThatThrownBy(() -> sut.setName(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.NAME_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should capitalize name and trim spaces when setting name")
    void setName_ShouldCapitalizeAndTrim() {
        String input = "  jaVA   WOrkSHop  ";
        String expected = "Java Workshop";

        sut.setName(input);

        assertThat(sut.getName()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception when setting type to null")
    void setType_ShouldThrowException_WhenNull() {
        assertThatThrownBy(() -> sut.setType(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.TYPE_REQUIRED.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @DisplayName("Should throw exception when setting an invalid organizer")
    void setOrganizer_ShouldThrowException_WhenValueIsInvalid(String invalidOrg) {
        assertThatThrownBy(() -> sut.setOrganizer(invalidOrg))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.ORGANIZER_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should trim and clean organizer name when setting it")
    void setOrganizer_ShouldTrimAndClean() {
        String input = "   Organizer    Name  Inc.   ";
        String expected = "Organizer Name Inc.";

        sut.setOrganizer(input);

        assertThat(sut.getOrganizer()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should trim and clean location when setting it")
    void setLocation_ShouldTrimAndClean() {
        String input = "  Main   Street, 123 \n ";
        String expected = "Main Street, 123";

        sut.setLocation(input);

        assertThat(sut.getLocation()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw exception when start date is null")
    void setStartDate_ShouldThrowException_WhenNull() {
        assertThatThrownBy(() -> sut.setStartDate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.START_DATE_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should throw GlobalAppException when start date is after end date")
    void setStartDate_ShouldThrowException_WhenAfterEndDate() {
        OffsetDateTime invalidStart = sut.getEndDate().plusDays(1);

        assertThatThrownBy(() -> sut.setStartDate(invalidStart))
                .isInstanceOf(GlobalAppException.class);
    }

    @Test
    @DisplayName("Should throw GlobalAppException when end date is before start date")
    void setEndDate_ShouldThrowException_WhenBeforeStartDate() {
        OffsetDateTime invalidEnd = sut.getStartDate().minusDays(1);

        assertThatThrownBy(() -> sut.setEndDate(invalidEnd))
                .isInstanceOf(GlobalAppException.class);
    }

    // --- PARTICIPANT METHODS ---

    @Test
    @DisplayName("Should add a participant successfully")
    void shouldAddParticipantSuccessfully() {
        UUID id = UUID.randomUUID();

        sut.addParticipant(id);

        assertThat(sut.getParticipantIds())
                .contains(id);
        assertEquals(1, sut.getParticipantIds().size());
    }

    @Test
    @DisplayName("Should throw exception when adding a null participant ID")
    void shouldThrowExceptionWhenParticipantIdIsNull() {
        assertThatThrownBy(() -> sut.addParticipant(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.PARTICIPANT_ID_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should not allow duplicate participants")
    void shouldNotAllowDuplicateParticipants() {
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        sut.addParticipant(id);
        sut.addParticipant(id2);
        sut.addParticipant(id); // Try to add same

        assertEquals(2, sut.getParticipantIds().size(),
                "The collection should not contain duplicate participant IDs");
    }

    @Test
    @DisplayName("Should remove an existing participant successfully")
    void shouldRemoveParticipantSuccessfully() {
        UUID id = UUID.randomUUID();

        sut.addParticipant(id);

        sut.removeParticipant(id);

        assertThat(sut.getParticipantIds())
                .doesNotContain(id);
        assertEquals(0, sut.getParticipantIds().size());
    }

    @Test
    @DisplayName("Should throw exception when participant ID to remove is null")
    void shouldThrowExceptionWhenRemovingNullParticipantId() {
        assertThatThrownBy(() -> sut.removeParticipant(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.PARTICIPANT_ID_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("Should throw GlobalAppException when participant to remove is not found")
    void shouldThrowExceptionWhenParticipantNotFound() {
        UUID id = UUID.randomUUID();

        assertThatThrownBy(() -> sut.removeParticipant(id))
                .isInstanceOf(GlobalAppException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    @DisplayName("Should return true when the participant is the owner")
    void shouldReturnTrueWhenParticipantIsOwner() {
        boolean isOwner = sut.participantIsOwner(sutOwnerId);
        assertThat(isOwner).isTrue();
    }

    @Test
    @DisplayName("Should return false when the participant is not the owner")
    void shouldReturnFalseWhenParticipantIsNotOwner() {
        boolean isOwner = sut.participantIsOwner(UUID.randomUUID());
        assertThat(isOwner).isFalse();
    }

    @Test
    @DisplayName("Should throw exception when checking a null participant ID")
    void shouldThrowExceptionWhenCheckingNullParticipantId() {
        assertThatThrownBy(() -> sut.participantIsOwner(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EventMessages.PARTICIPANT_ID_REQUIRED.getMessage());
    }
}