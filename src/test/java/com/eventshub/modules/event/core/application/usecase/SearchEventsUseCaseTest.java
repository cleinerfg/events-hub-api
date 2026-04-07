package com.eventshub.modules.event.core.application.usecase;

import com.eventshub.modules.event.core.application.port.EventPort;
import com.eventshub.modules.event.core.application.usecase.query.SearchEventQuery;
import com.eventshub.modules.event.core.domain.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchEventsUseCaseTest {

    @Mock
    private EventPort port;

    @InjectMocks
    private SearchEventsUseCase sut;

    @Test
    @DisplayName("Should return a list of events when search matches criteria")
    void shouldReturnEventsWhenSearchMatches() {
        var query = SearchEventQuery.builder()
                .name("Any name")
                .build();

        List<Event> expected = List.of(mock(Event.class));

        when(port.search(query)).thenReturn(expected);

        List<Event> result = sut.execute(query);

        assertThat(result).hasSize(1).isEqualTo(expected);
        verify(port).search(query);
    }

    @Test
    @DisplayName("Should return empty list when no events match criteria")
    void shouldReturnEmptyListWhenNoMatch() {
        var query = SearchEventQuery.builder()
                .name("Non existing")
                .build();

        when(port.search(query)).thenReturn(Collections.emptyList());

        List<Event> result = sut.execute(query);

        assertThat(result).isEmpty();
        verify(port).search(query);
    }
}