package com.eventshub.event.infra.config;

import com.eventshub.event.core.gateway.EventGateway;
import com.eventshub.event.core.usecases.CreateEventUseCase;
import com.eventshub.event.core.usecases.FindAllEventsUseCase;
import com.eventshub.event.core.usecases.FindEventByIdentifierUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

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
}
