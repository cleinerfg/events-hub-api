package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.application.usecase.*;
import com.eventshub.modules.event.core.domain.model.Event;
import com.eventshub.modules.event.infra.web.dto.CreateEventRequest;
import com.eventshub.modules.event.infra.web.dto.EventResponse;
import com.eventshub.modules.event.infra.web.dto.SearchEventRequest;
import com.eventshub.modules.event.infra.web.dto.UpdateEventRequest;
import com.eventshub.shared.infra.security.SecurityContextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/events")
public class EventController {

    private final CreateEventUseCase createUseCase;
    private final FindEventByIdUseCase findByIdUseCase;
    private final FindAllEventsUseCase findAllUseCase;
    private final SearchEventsUseCase searchUseCase;
    private final UpdateEventUseCase updateUseCase;
    private final DeleteEventUseCase deleteUseCase;
    private final AddParticipantEventUseCase addParticipantUseCase;
    private final RemoveParticipantEventUseCase removeParticipantUseCase;

    private final EventWebMapper mapper;
    private final SecurityContextService securityContextService;

    @PostMapping
    public ResponseEntity<EventResponse> create(
            @RequestBody @Valid CreateEventRequest request
    ) {
        UUID ownerId = securityContextService
                .getAuthenticatedPayload()
                .externalId();

        Event event = createUseCase.execute(
                mapper.toCreateCommand(request, ownerId)
        );
        EventResponse response = mapper.toResponse(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(event.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<EventResponse> findByExternalId(
            @PathVariable UUID externalId
    ) {
        Event event = findByIdUseCase.execute(externalId);
        return ResponseEntity.ok(mapper.toResponse(event));
    }

    @GetMapping
    public List<EventResponse> findAll() {
        return findAllUseCase.execute()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @GetMapping("/search")
    public List<EventResponse> search(
            @ModelAttribute @Valid SearchEventRequest request
    ) {
        return searchUseCase.execute(mapper.toSearchQuery(request))
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<EventResponse> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid UpdateEventRequest request
    ) {
        Event event = updateUseCase.execute(
                externalId,
                mapper.toUpdateCommand(request)
        );
        return ResponseEntity.ok(mapper.toResponse(event));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID externalId
    ) {
        deleteUseCase.execute(externalId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{externalId}/participants/join")
    public ResponseEntity<Void> participantJoin(
            @PathVariable UUID externalId
    ) {
        UUID participantId = securityContextService
                .getAuthenticatedPayload()
                .externalId();

        addParticipantUseCase.execute(externalId, participantId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{externalId}/participants/leave")
    public ResponseEntity<Void> participantLeave(
            @PathVariable UUID externalId
    ) {
        UUID participantId = securityContextService
                .getAuthenticatedPayload()
                .externalId();

        removeParticipantUseCase.execute(externalId, participantId);
        return ResponseEntity.ok().build();
    }
}
