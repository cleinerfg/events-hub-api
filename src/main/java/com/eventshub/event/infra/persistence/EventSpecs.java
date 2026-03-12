package com.eventshub.event.infra.persistence;

import com.eventshub.event.core.model.input.SearchEventInput;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public final class EventSpecs {

    private EventSpecs() {
    }

    public static Specification<EventJpaEntity> from(SearchEventInput input) {
        return (root, query, cb) -> {

            // Local list to each execution (Thread-safe)
            List<Predicate> predicates = new ArrayList<>();

            addLike(predicates, cb, root.get("name"), input.name());
            addEqual(predicates, cb, root.get("type"), input.type());
            addLike(predicates, cb, root.get("description"), input.description());
            addEqual(predicates, cb, root.get("organizer"), input.organizer());
            addLike(predicates, cb, root.get("location"), input.location());

            // TODO: fix filter by date

            Expression<OffsetDateTime> startDate = root.get("startDate");
            addGreaterThanOrEqual(predicates, cb, startDate, input.startDate());

            Expression<OffsetDateTime> endDate = root.get("endDate");
            addLessThanOrEqual(predicates, cb, endDate, input.endDate());

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLike(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Expression<String> field,
            String value
    ) {

        if (value != null && !value.isBlank()) {
            predicates.add(cb.like(cb.lower(field), "%" + value.toLowerCase() + "%"));
        }
    }

    private static <T> void addEqual(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Expression<T> field,
            Object value
    ) {

        if (value != null) {
            predicates.add(cb.equal(field, value));
        }
    }

    // CriteriaBuilder's comparison methods require Y extends Comparable to guarantee
    // the type has a natural ordering. Using Object would break type-safety since
    // Object doesn't implement Comparable, causing a compile-time error.

    private static <Y extends Comparable<? super Y>> void addGreaterThanOrEqual(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Expression<Y> field,
            Y value) {

        if (value != null) {
            predicates.add(cb.greaterThanOrEqualTo(field, value));
        }
    }

    private static <Y extends Comparable<? super Y>> void addLessThanOrEqual(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Expression<Y> field,
            Y value) {

        if (value != null) {
            predicates.add(cb.lessThanOrEqualTo(field, value));
        }
    }
}