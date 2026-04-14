package com.eventshub.modules.event.infra.web.dto;

import com.eventshub.modules.event.core.domain.dto.EventSummary;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record EventsSummaryResponse(
        UUID ownerId,
        List<EventSummary> events
) {
}
