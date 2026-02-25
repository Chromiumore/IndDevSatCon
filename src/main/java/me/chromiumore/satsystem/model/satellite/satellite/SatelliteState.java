package me.chromiumore.satsystem.model.satellite.satellite;

import lombok.Getter;

public class SatelliteState {
    private boolean isActive = false;
    @Getter
    private String statusMessage;

    public SatelliteState() {
        statusMessage = "Не активирован";
    }

    public boolean activate(boolean hasSufficientPower) {
        if (hasSufficientPower && !isActive) {
            isActive = true;
            statusMessage = "Активен";
            return true;
        }

        statusMessage = hasSufficientPower ? "Уже активен" : "Недостаточно энергии";
        return false;
    }

    public void deactivate() {
        isActive = false;
        statusMessage = "Деактивирован";
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "SatelliteState{" +
                "isActive=" + isActive +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
