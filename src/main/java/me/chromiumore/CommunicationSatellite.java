package me.chromiumore;

public class CommunicationSatellite extends Satellite {
    private double bandwidth;

    public CommunicationSatellite(String name, double batteryLevel, double bandwidth) {
        super(name, batteryLevel);
        this.bandwidth = bandwidth;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    @Override
    public void performMission() {
        if (isActive) {
            System.out.printf("%s: Передача данных со скоростью %f Мбит/с\n", name, bandwidth);
            consumeBattery(0.05);
            sendData(bandwidth);
            System.out.printf("%s: Отправил %f Мбит данных!\n", name, bandwidth);
            return;
        }

        System.out.printf("\uD83D\uDED1 %s: Не может выполнить передачу данных - не активен\n", name);
    }

    private void sendData(double amount) {
        System.out.printf("%s: Передача данных со скоростью %f Мбит/с\n", name, bandwidth);
        System.out.printf("%s: Отправил %f Мбит данных!\n", name, amount);
    }

    @Override
    public String toString() {
        return String.format("CommunicationSatellite{bandwidth=%f, name='%s', isActive=%b, batteryLevel=%f}", bandwidth, name, isActive, batteryLevel);
    }
}
