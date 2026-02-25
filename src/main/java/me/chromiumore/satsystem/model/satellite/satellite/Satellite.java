package me.chromiumore.satsystem.model.satellite.satellite;

import lombok.Getter;
import lombok.Setter;

public abstract class Satellite {
    @Getter @Setter
    protected String name;
    protected SatelliteState state;
    protected EnergySystem energy;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.energy = new EnergySystem(batteryLevel);
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
}
