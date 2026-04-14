package com.eventshub.modules.event.infra.config;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.*;
import com.eventshub.shared.core.port.UuidGeneratorPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBeanConfig {

    @Bean
    public CreateEventUseCase createEventUseCase(
            EventPort port,
            UuidGeneratorPort uuidGeneratorPort
    ) {
        return new CreateEventUseCase(port, uuidGeneratorPort);
    }

    @Bean
    public FindEventByIdUseCase findEventByIdUseCase(EventPort port) {
        return new FindEventByIdUseCase(port);
    }

    @Bean
    public FindAllEventsUseCase findAllEventsUseCase(EventPort port) {
        return new FindAllEventsUseCase(port);
    }

    @Bean
    public SearchEventsUseCase searchEventsUseCase(EventPort port) {
        return new SearchEventsUseCase(port);
    }

    @Bean
    public UpdateEventUseCase updateEventUseCase(EventPort port) {
        return new UpdateEventUseCase(port);
    }

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventPort port) {
        return new DeleteEventUseCase(port);
    }

    @Bean
    public FindAllEventsByOwnerIdUseCase findAllEventsByOwnerIdUseCase(EventPort port) {
        return new FindAllEventsByOwnerIdUseCase(port);
    }

    @Bean
    public FindAllParticipantsEventUseCase findAllParticipantsEventUseCase(EventPort port) {
        return new FindAllParticipantsEventUseCase(port);
    }

    @Bean
    public AddParticipantEventUseCase addParticipantUseCase(EventPort port) {
        return new AddParticipantEventUseCase(port);
    }

    @Bean
    public RemoveParticipantEventUseCase removeParticipantEventUseCase(EventPort port) {
        return new RemoveParticipantEventUseCase(port);
    }
}
