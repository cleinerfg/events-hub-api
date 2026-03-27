package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.core.domain.model.input.CreateEventInput;
import com.eventshub.modules.event.core.domain.model.input.SearchEventInput;
import com.eventshub.modules.event.core.domain.model.input.UpdateEventInput;
import com.eventshub.modules.event.infra.web.dto.CreateEventRequest;
import com.eventshub.modules.event.infra.web.dto.EventResponse;
import com.eventshub.modules.event.infra.web.dto.SearchEventRequest;
import com.eventshub.modules.event.infra.web.dto.UpdateEventRequest;
import com.eventshub.shared.core.support.StringSanitizer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventWebMapper {

    public EventResponse toResponse(Event event) {
        return EventResponse.builder()
                .name(event.getName())
                .externalId(event.getExternalId())
                .ownerExternalId(event.getOwnerExternalId())
                .type(event.getType())
                .description(event.getDescription())
                .organizer(event.getOrganizer())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }

    public CreateEventInput toCreateInput(CreateEventRequest request, UUID ownerId) {
        return CreateEventInput.builder()
                .ownerExternalId(ownerId)
                .name(StringSanitizer.capitalize(request.name()))
                .type(request.type())
                .description(StringSanitizer.trimAndClean(request.description()))
                .organizer(StringSanitizer.trimAndClean(request.organizer()))
                .location(StringSanitizer.trimAndClean(request.location()))
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public SearchEventInput toSearchInput(SearchEventRequest request) {
        return SearchEventInput.builder()
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

    public UpdateEventInput toUpdateInput(UpdateEventRequest request) {
        return UpdateEventInput.builder()
                .name(StringSanitizer.capitalize(request.name()))
                .type(request.type())
                .description(StringSanitizer.trimAndClean(request.description()))
                .organizer(StringSanitizer.trimAndClean(request.organizer()))
                .location(StringSanitizer.trimAndClean(request.location()))
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }
}
