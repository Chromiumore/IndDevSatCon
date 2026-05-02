package me.chromiumore.satsystem.domain.satellite;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "energy_system")
@Builder
@Getter @Setter
public class EnergySystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "battery_level", nullable = false)
    private double batteryLevel;
    @Column(name = "low_battery_threshold", nullable = false)
    private double lowBatteryThreshold;
    @Column(name = "max_battery", nullable = false)
    private double maxBattery;
    @Column(name = "min_battery", nullable = false)
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
