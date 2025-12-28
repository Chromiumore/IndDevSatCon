package me.chromiumore;

public class ImagingSatellite extends Satellite{
    private double resolution;
    private int photosTaken;

    public ImagingSatellite(String name, double batteryLevel, double resolution) {
        super(name, batteryLevel);
        this.resolution = resolution;
        photosTaken = 0;
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
        return String.format("ImagingSatellite{resolution=%f, photosTaken=%d, name='%s', isActive=%b, batteryLevel=%f}", resolution, photosTaken, name, isActive, batteryLevel);
    }
}
