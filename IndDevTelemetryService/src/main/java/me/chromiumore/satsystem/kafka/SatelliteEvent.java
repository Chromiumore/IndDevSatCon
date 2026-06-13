package me.chromiumore.satsystem.kafka;

import java.time.Instant;
import java.util.UUID;

public record SatelliteEvent(
        UUID eventId,
        Long satelliteId,
        String satelliteName,
        EventType eventType,
        Instant timestamp
) {
    public enum EventType { CREATED, DELETED }
}
