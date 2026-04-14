package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.application.usecase.command.CreateEventCommand;
import com.eventshub.modules.event.core.application.usecase.command.UpdateEventCommand;
import com.eventshub.modules.event.core.application.usecase.query.SearchEventQuery;
import com.eventshub.modules.event.core.domain.dto.ParticipantEvent;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.infra.web.dto.*;
import com.eventshub.shared.core.support.StringSanitizer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EventWebMapper {

    public EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .ownerId(event.getOwnerId())
                .name(event.getName())
                .type(event.getType())
                .description(event.getDescription())
                .organizer(event.getOrganizer())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }

    public ParticipantsEventResponse toParticipantsResponse(
            UUID id, List<ParticipantEvent> participantsEvent
    ) {
        return ParticipantsEventResponse.builder()
                .eventId(id)
                .participants(participantsEvent)
                .build();
    }

    public CreateEventCommand toCreateCommand(CreateEventRequest request, UUID ownerId) {
        return CreateEventCommand.builder()
                .ownerId(ownerId)
                .name(StringSanitizer.trimAndClean(request.name()))
                .type(request.type())
                .description(StringSanitizer.trimAndClean(request.description()))
                .organizer(StringSanitizer.trimAndClean(request.organizer()))
                .location(StringSanitizer.trimAndClean(request.location()))
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public SearchEventQuery toSearchQuery(SearchEventRequest request) {
        return SearchEventQuery.builder()
                .name(StringSanitizer.trimAndClean(request.name()))
                .type(request.type())
                .description(StringSanitizer.trimAndClean(request.description()))
                .organizer(StringSanitizer.trimAndClean(request.organizer()))
                .location(StringSanitizer.trimAndClean(request.location()))
                .startDateFrom(request.startDateFrom())
                .startDateUntil(request.startDateUntil())
                .endDateFrom(request.endDateFrom())
                .endDateUntil(request.endDateUntil())
                .build();
    }

    public UpdateEventCommand toUpdateCommand(UpdateEventRequest request) {
        return UpdateEventCommand.builder()
                .name(StringSanitizer.trimAndClean(request.name()))
                .type(request.type())
                .description(StringSanitizer.trimAndClean(request.description()))
                .organizer(StringSanitizer.trimAndClean(request.organizer()))
                .location(StringSanitizer.trimAndClean(request.location()))
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }
}
