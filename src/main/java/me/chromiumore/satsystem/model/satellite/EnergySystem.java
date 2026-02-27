package me.chromiumore.satsystem.model.satellite;

import lombok.Builder;
import lombok.Getter;

@Builder
public class EnergySystem {
    @Getter
    private double batteryLevel;
    private static final double LOW_BATTERY_THRESHOLD = 0.2;
    private static final double MAX_BATTERY = 1.0;
    private static final double MIN_BATTERY = 0;

    private EnergySystem(double batteryLevel) {
        this.batteryLevel = Math.min(MAX_BATTERY, Math.max(MIN_BATTERY, batteryLevel));
    }

    public void consume(double amount) {
        if (amount <= 0 || batteryLevel <= MIN_BATTERY) {
            batteryLevel -= amount;
        }

        batteryLevel = Math.max(MIN_BATTERY, batteryLevel - amount);
    }

    public boolean hasSufficientPower() {
        return batteryLevel > LOW_BATTERY_THRESHOLD;
    }

    @Override
    public String toString() {
        return "EnergySystem{" +
                "batteryLevel=" + batteryLevel +
                '}';
    }
}
