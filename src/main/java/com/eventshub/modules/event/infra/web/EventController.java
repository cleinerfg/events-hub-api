package com.eventshub.modules.event.infra.web;

import com.eventshub.modules.event.core.model.Event;
import com.eventshub.modules.event.core.model.input.SearchEventInput;
import com.eventshub.modules.event.core.model.input.UpdateEventInput;
import com.eventshub.modules.event.core.usecase.*;
import com.eventshub.modules.event.infra.web.dto.EventRequest;
import com.eventshub.modules.event.infra.web.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/events")
public class EventController {

    private final CreateEventUseCase createUseCase;
    private final FindEventByIdentifierUseCase findByIdentifierUseCase;
    private final FindAllEventsUseCase findAllUseCase;
    private final SearchEventsUseCase searchUseCase;
    private final UpdateEventUseCase updateUseCase;
    private final DeleteEventUseCase deleteUseCase;
    private final EventDtoMapper dtoMapper;

    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody EventRequest request) {
        Event event = createUseCase.execute(dtoMapper.toDomain(request));
        EventResponse response = dtoMapper.toResponse(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{identifier}")
                .buildAndExpand(event.getIdentifier())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<EventResponse> findByIdentifier(
            @PathVariable String identifier
    ) {
        Event event = findByIdentifierUseCase.execute(identifier);
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
            @ModelAttribute SearchEventInput input
    ) {
        return searchUseCase.execute(input)
                .stream()
                .map(dtoMapper::toResponse)
                .toList();
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<EventResponse> update(
            @PathVariable String identifier,
            @RequestBody UpdateEventInput request
    ) {
        Event updatedEvent = updateUseCase.execute(identifier, request);
        return ResponseEntity.ok(dtoMapper.toResponse(updatedEvent));
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> delete(
            @PathVariable String identifier
    ) {
        deleteUseCase.execute(identifier);
        return ResponseEntity.noContent().build();
    }
}
