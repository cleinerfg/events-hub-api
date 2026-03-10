package com.eventshub.event.infra;

import com.eventshub.event.core.model.Event;
import com.eventshub.event.core.usecases.CreateEventUseCase;
import com.eventshub.event.core.usecases.FindAllEventsUseCase;
import com.eventshub.event.core.usecases.FindEventByIdentifierUseCase;
import com.eventshub.event.infra.dto.EventRequest;
import com.eventshub.event.infra.dto.EventResponse;
import com.eventshub.event.infra.mapper.EventDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final FindEventByIdentifierUseCase findEventByIdentifierUseCase;
    private final FindAllEventsUseCase findAllEventsUseCase;
    private final EventDtoMapper eventDtoMapper;

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody EventRequest request) {
        Event event = createEventUseCase.execute(eventDtoMapper.toDomain(request));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event created successfully");
        response.put("event", eventDtoMapper.toResponse(event));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<EventResponse> findByIdentifier(
            @PathVariable String identifier
    ) {
        Event event = findEventByIdentifierUseCase.execute(identifier);
        return ResponseEntity.ok(eventDtoMapper.toResponse(event));
    }

    @GetMapping
    public List<EventResponse> findAll() {
        return findAllEventsUseCase.execute()
                .stream()
                .map(eventDtoMapper::toResponse)
                .toList();
    }
}
