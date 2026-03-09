package com.eventshub.infra.beans;

import com.eventshub.core.gateway.EventGateway;
import com.eventshub.core.usecases.CreateEventUseCase;
import com.eventshub.core.usecases.CreateEventUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    public CreateEventUseCase createEventUseCase(EventGateway eventGateway) {
        return new CreateEventUseCaseImpl(eventGateway);
    }
}
