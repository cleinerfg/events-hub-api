package com.eventshub.infra.beans;

import com.eventshub.core.gateway.EventGateway;
import com.eventshub.core.usecases.*;
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
