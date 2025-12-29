package me.chromiumore;

public abstract class Satellite {
    protected String name;
    protected boolean isActive;
    protected double batteryLevel;

    public Satellite(String name, double batteryLevel) {
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.isActive = false;
        System.out.println("Создан спутник: " + String.format("%s (заряд: %d%%)", name, (int) (batteryLevel * 100)));
    }

    protected abstract void performMission();

    public boolean activate() {
        if (batteryLevel > 0.2) {
            isActive = true;
        }

        if (isActive) {
            System.out.printf("✅ %s: Активация успешна\n", name);
        } else {
            System.out.printf("\uD83D\uDED1 %s: Ошибка активации (заряд: %d%%)\n", name, (int) (batteryLevel * 100));
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

        if (batteryLevel <= 0.2) {
            deactivate();
        }
    }
}
