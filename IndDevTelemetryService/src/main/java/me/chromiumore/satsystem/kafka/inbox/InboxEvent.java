package me.chromiumore.satsystem.kafka.inbox;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inbox")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InboxEvent {
    @Id
    @Column(name = "event_id", unique = true, nullable = false)
    private UUID eventId;

    @Column(name = "aggregate_id")
    private Long aggregateId;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}
