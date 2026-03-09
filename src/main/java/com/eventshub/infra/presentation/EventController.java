package com.eventshub.infra.presentation;

import com.eventshub.core.domain.Event;
import com.eventshub.core.usecases.CreateEventUseCase;
import com.eventshub.core.usecases.FindAllEventsUseCase;
import com.eventshub.infra.dto.EventRequest;
import com.eventshub.infra.dto.EventResponse;
import com.eventshub.infra.mapper.EventDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final FindAllEventsUseCase findAllEventsUseCase;
    private final EventDtoMapper eventDtoMapper;

    @PostMapping
    public EventResponse create(@RequestBody EventRequest request) {
        Event event = createEventUseCase.execute(eventDtoMapper.toDomain(request));
        return eventDtoMapper.toResponse(event);
    }

    @GetMapping
    public List<EventResponse> findAll() {
        return findAllEventsUseCase.execute()
                .stream()
                .map(eventDtoMapper::toResponse)
                .toList();
    }
}
