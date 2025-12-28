package me.chromiumore;

public class ImagingSatellite extends Satellite{
    private double resolution;
    private int photosTaken;

    public ImagingSatellite(String name, double batteryLevel) {
        super(name, batteryLevel);
    }

    public double getResolution() {
        return resolution;
    }

    public int getPhotosTaken() {
        return photosTaken;
    }

    @Override
    public void performMission() {
        if (isActive) {
            consumeBattery(0.08);
            takePhoto();
        }
    }

    private void takePhoto() {
        photosTaken++;
    }

    @Override
    public String toString() {
        return String.format("ДЗЗ-%s (заряд: %d%%)\n", name, (int) batteryLevel * 100);
    }
}
