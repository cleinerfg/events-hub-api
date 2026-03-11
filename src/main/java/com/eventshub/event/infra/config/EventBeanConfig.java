package com.eventshub.event.infra.config;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBeanConfig {

    @Bean
    public CreateEventUseCase createEventUseCase(EventGateway eventGateway) {
        return new CreateEventUseCase(eventGateway);
    }

    @Bean
    public FindEventByIdentifierUseCase findEventByIdentifierUseCase(EventGateway eventGateway) {
        return new FindEventByIdentifierUseCase(eventGateway);
    }

    @Bean
    public FindAllEventsUseCase findAllEventsUseCase(EventGateway eventGateway) {
        return new FindAllEventsUseCase(eventGateway);
    }

    @Bean
    public UpdateEventUseCase updateEventUseCase(EventGateway eventGateway) {
        return new UpdateEventUseCase(eventGateway);
    }

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventGateway eventGateway) {
        return new DeleteEventUseCase(eventGateway);
    }
}
