package me.chromiumore.satsystem.kafka.inbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface InboxEventRepository extends JpaRepository<InboxEvent, UUID> {
    boolean existsByEventId(UUID eventId);
}
