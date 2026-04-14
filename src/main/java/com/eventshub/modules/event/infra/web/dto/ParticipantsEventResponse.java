package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.domain.dto.ParticipantEvent;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ParticipantsEventResponse(
        UUID eventId,
        List<ParticipantEvent> participants
) {
}
