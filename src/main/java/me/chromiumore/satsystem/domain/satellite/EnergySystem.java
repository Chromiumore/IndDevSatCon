package me.chromiumore.satsystem.domain.satellite;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EnergySystem {
    private double batteryLevel;
    private double lowBatteryThreshold;
    private double maxBattery;
    private double minBattery;

    public static EnergySystemBuilder builder() {
        return new CustomEnergySystemBuilder();
    }

    private static class CustomEnergySystemBuilder extends EnergySystemBuilder {
        @Override
        public EnergySystem build() {
            super.batteryLevel = Math.min(super.maxBattery, Math.max(super.minBattery, super.batteryLevel));
            return super.build();
        }
    }

    public void consume(double amount) {
        if (amount <= 0 || batteryLevel <= minBattery) {
            batteryLevel -= amount;
        }

        batteryLevel = Math.max(minBattery, batteryLevel - amount);
    }

    public boolean hasSufficientPower() {
        return batteryLevel > lowBatteryThreshold;
    }

    @Override
    public String toString() {
        return "EnergySystem{" +
                "batteryLevel=" + batteryLevel +
                '}';
    }
}
