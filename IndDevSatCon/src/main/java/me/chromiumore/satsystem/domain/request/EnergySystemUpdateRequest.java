package me.chromiumore.satsystem.domain.request;

public record EnergySystemUpdateRequest(
        Double batteryLevel,
        Double lowBatteryThreshold,
        Double minBattery,
        Double maxBattery
) {
}
