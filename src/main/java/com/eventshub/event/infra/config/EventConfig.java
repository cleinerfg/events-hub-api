package com.eventshub.event.infra.config;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    public CreateEventUseCase createEventUseCase(EventGateway eventGateway) {
        return new CreateEventUseCaseImpl(eventGateway);
    }

    @Bean
    public FindEventByIdentifierUseCase findEventByIdentifierUseCase(EventGateway eventGateway) {
        return new FindEventByIdentifierUseCaseImpl(eventGateway);
    }

    @Bean
    public FindAllEventsUseCase findAllEventsUseCase(EventGateway eventGateway) {
        return new FindAllEventsUseCaseImpl(eventGateway);
    }
}
