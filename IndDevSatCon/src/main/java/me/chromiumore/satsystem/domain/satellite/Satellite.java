package me.chromiumore.satsystem.domain.satellite;

import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.constant.EnergySystemConstants;

public abstract class Satellite {
    @Getter @Setter
    protected String name;
    protected SatelliteState state;
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
