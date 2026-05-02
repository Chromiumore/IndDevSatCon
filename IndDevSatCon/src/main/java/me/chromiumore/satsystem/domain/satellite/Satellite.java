package me.chromiumore.satsystem.domain.satellite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chromiumore.satsystem.constant.EnergySystemConstants;
import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;

@Entity
@Table(name = "satellite")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "satellite_type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constellation_id")
    protected SatelliteConstellation constellation;

    @Embedded
    protected SatelliteState state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "energy_id", unique = true)
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = EnergySystem.builder()
                .batteryLevel(batteryLevel)
                .minBattery(EnergySystemConstants.MIN_BATTERY)
                .maxBattery(EnergySystemConstants.MAX_BATTERY)
                .lowBatteryThreshold(EnergySystemConstants.LOW_BATTERY_THRESHOLD)
                .build();
        this.state = new SatelliteState();
        System.out.println("Создан спутник: " + String.format("%s (заряд: %d%%)", name, (int) (energy.getBatteryLevel() * 100)));
    }

    public abstract void performMission();

    public boolean activate() {
        if (state.activate(energy.hasSufficientPower())) {
            System.out.printf("✅ %s: Активация успешна\n", name);
            return true;
        }
        System.out.printf("\uD83D\uDED1 %s: Ошибка активации (заряд: %d%%)\n", name, (int) (energy.getBatteryLevel() * 100));
        return false;
    }

    public void deactivate() {
        if (state.isActive()) {
            state.deactivate();
        }
    }

    public String getStatus() {
        return state.getStatusMessage();
    }

    public boolean isActive() {
        return state.isActive();
    }

    public double getBatteryLevel() {
        return energy.getBatteryLevel();
    }
}
