package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.usecase.*;
import com.eventshub.modules.event.infra.web.dto.CreateEventRequest;
import com.eventshub.modules.event.infra.web.dto.EventResponse;
import com.eventshub.modules.event.infra.web.dto.SearchEventRequest;
import com.eventshub.modules.event.infra.web.dto.UpdateEventRequest;
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
    private final FindEventByExternalIdUseCase findByExternalIdUseCase;
    private final FindAllEventsUseCase findAllUseCase;
    private final SearchEventsUseCase searchUseCase;
    private final UpdateEventUseCase updateUseCase;
    private final DeleteEventUseCase deleteUseCase;
    private final EventDtoMapper dtoMapper;

    @PostMapping
    public ResponseEntity<EventResponse> create(
            @RequestBody @Valid CreateEventRequest request
    ) {
        Event event = createUseCase.execute(
                dtoMapper.toCreateInput(request)
        );
        EventResponse response = dtoMapper.toResponse(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{externalId}")
                .buildAndExpand(event.getExternalId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<EventResponse> findByExternalId(
            @PathVariable UUID externalId
    ) {
        Event event = findByExternalIdUseCase.execute(externalId);
        return ResponseEntity.ok(dtoMapper.toResponse(event));
    }

    @GetMapping
    public List<EventResponse> findAll() {
        return findAllUseCase.execute()
                .stream()
                .map(dtoMapper::toResponse)
                .toList();
    }

    @GetMapping("/search")
    public List<EventResponse> search(
            @ModelAttribute @Valid SearchEventRequest request
    ) {
        return searchUseCase.execute(dtoMapper.toSearchInput(request))
                .stream()
                .map(dtoMapper::toResponse)
                .toList();
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<EventResponse> update(
            @PathVariable UUID externalId,
            @RequestBody @Valid UpdateEventRequest request
    ) {
        Event updatedEvent = updateUseCase.execute(
                externalId,
                dtoMapper.toUpdateInput(request)
        );
        return ResponseEntity.ok(dtoMapper.toResponse(updatedEvent));
    }

    @DeleteMapping("/{externalId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID externalId
    ) {
        deleteUseCase.execute(externalId);
        return ResponseEntity.noContent().build();
    }
}
