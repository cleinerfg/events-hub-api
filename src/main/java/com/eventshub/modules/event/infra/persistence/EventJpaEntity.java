package com.eventshub.modules.event.infra.persistence;

import com.eventshub.modules.event.core.domain.model.EventType;
import com.eventshub.modules.user.infra.persistence.UserJpaEntity;
import com.eventshub.shared.core.exception.GlobalAppException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "event")
public class EventJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true, updatable = false)
    private UUID externalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserJpaEntity owner;

    @Column(nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "event_type")
    private EventType type;

    private String description;

    @Column(nullable = false)
    private String organizer;

    @Column(nullable = false)
    private String location;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name = "end_date")
    private OffsetDateTime endDate;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserJpaEntity> participants = new HashSet<>();

    public void addParticipant(UserJpaEntity user) {
        if (this.participants == null)
            throw GlobalAppException.systemIntegrity("Participant cannot be null");
        this.participants.add(user);
    }

    public void removeParticipant(UserJpaEntity user) {
        if (this.participants == null)
            throw GlobalAppException.systemIntegrity("Participant cannot be null");
        this.participants.remove(user);
    }
}
