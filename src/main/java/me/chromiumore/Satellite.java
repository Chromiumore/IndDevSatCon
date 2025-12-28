package me.chromiumore;

public abstract class Satellite {
    protected String name;
    protected boolean isActive;
    protected double batteryLevel;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.isActive = false;
        System.out.println("Создан спутник: " + this);
    }

    protected abstract void performMission();

    public boolean activate() {
        if (batteryLevel > 0.2) {
            isActive = true;
        }
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    public void consumeBattery(double amount) {
        if (batteryLevel - amount > 0) {
            batteryLevel -= amount;
        } else {
            batteryLevel = 0;
        }

        if (batteryLevel <= 20) {
            deactivate();
        }
    }
}
