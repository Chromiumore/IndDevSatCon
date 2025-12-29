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
            return;
        }

        System.out.printf("\uD83D\uDED1 %s: Не может выполнить съемку - не активен\n", name);
    }

    private void takePhoto() {
        System.out.printf("%s: Съемка территории с разрешением %.1f м/пиксель\n", name, resolution);
        photosTaken++;
        System.out.printf("%s: Снимок #%d сделан!\n", name, photosTaken);
    }

    @Override
    public String toString() {
        return String.format("ImagingSatellite{resolution=%.1f, photosTaken=%d, name='%s', isActive=%b, batteryLevel=%.2f}", resolution, photosTaken, name, isActive, batteryLevel);
    }
}
