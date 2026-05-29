package me.chromiumore.satsystem.kafka;

import jdk.jfr.EventType;

import java.time.Instant;

public record SatelliteEvent(
        Long satelliteId,
        String satelliteName,
        EventType eventType,
        Instant timestamp
) {
    public enum EventType { CREATED, DELETED }
}
