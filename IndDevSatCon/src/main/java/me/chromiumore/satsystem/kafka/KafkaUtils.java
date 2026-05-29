package me.chromiumore.satsystem.kafka;

import lombok.experimental.UtilityClass;
import me.chromiumore.satsystem.domain.satellite.Satellite;

import java.time.Instant;

@UtilityClass
public class KafkaUtils {
    public static SatelliteEvent createEvent(Satellite satellite, SatelliteEvent.EventType eventType) {
        return new SatelliteEvent(
                satellite.getId(), satellite.getName(), eventType, Instant.now()
        );
    }
}
