package me.chromiumore.satsystem.kafka.inbox;

import lombok.experimental.UtilityClass;
import me.chromiumore.satsystem.kafka.SatelliteEvent;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class InboxUtils {
    public InboxEvent createInboxEvent(SatelliteEvent event) {
        return InboxEvent.builder()
                .eventId(generateStableEventId(event))
                .aggregateId(event.satelliteId())
                .eventType(event.eventType().name())
                .processedAt(LocalDateTime.now())
                .build();
    }

    public UUID generateStableEventId(SatelliteEvent event) {
        String key = event.satelliteId() + ":" +
                event.eventType() + ":" +
                event.timestamp();

        return UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    }
}
