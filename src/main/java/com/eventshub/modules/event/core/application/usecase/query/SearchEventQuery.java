package com.eventshub.modules.event.core.application.usecase.query;

import com.eventshub.modules.event.core.domain.model.EventType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Builder
public record SearchEventQuery(
        String name,
        EventType type,
        String description,
        String organizer,
        String location,
        LocalDate startDateFrom,
        LocalDate startDateUntil,
        LocalDate endDateFrom,
        LocalDate endDateUntil
) {
    public OffsetDateTime getStartDateFromAsDateTime() {
        return dateFromAsDateTime(startDateFrom);

    }

    public OffsetDateTime getStartDateUntilAsDateTime() {
        return dateUntilAsDateTime(startDateUntil);
    }

    public OffsetDateTime getEndDateFromAsDateTime() {
        return dateFromAsDateTime(endDateFrom);

    }

    public OffsetDateTime getEndDateUntilAsDateTime() {
        return dateUntilAsDateTime(endDateUntil);
    }


    private OffsetDateTime dateFromAsDateTime(LocalDate dateFrom) {
        return dateFrom != null
                ? dateFrom.atStartOfDay().atOffset(ZoneOffset.UTC)
                : null;
    }

    private OffsetDateTime dateUntilAsDateTime(LocalDate dateUntil) {
        return dateUntil != null
                ? dateUntil.atTime(23, 59, 59).atOffset(ZoneOffset.UTC)
                : null;
    }
}
