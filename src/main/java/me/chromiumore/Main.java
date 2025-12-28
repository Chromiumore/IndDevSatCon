package me.chromiumore;

public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ\n" +
                "============================================================");


        System.out.println("СОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:\n" +
                "---------------------------------------------");

        Satellite com1 = new CommunicationSatellite("Связь-1", 0.85, 500);
        Satellite com2 = new CommunicationSatellite("Связь-2", 0.75, 1000);
        Satellite img1 = new ImagingSatellite("ДЗЗ-1", 0.92, 2.5);
        Satellite img2 = new ImagingSatellite("ДЗЗ-2", 0.45, 1);
        Satellite img3 = new ImagingSatellite("ДЗЗ-3", 0.15, 0.5);

        System.out.println("---------------------------------------------");


        SatelliteConstellation constellation = new SatelliteConstellation("RU Basic");


        System.out.println("ФОРМИРОВАНИЕ ГРУППИРОВКИ:\n" +
                "-----------------------------------");

        constellation.addSatellite(com1);
        constellation.addSatellite(com2);
        constellation.addSatellite(img1);
        constellation.addSatellite(img2);
        constellation.addSatellite(img3);

        System.out.println("-----------------------------------");

        System.out.println(constellation.getSatellites());
        System.out.println("-----------------------------------");


        System.out.println("АКТИВАЦИЯ СПУТНИКОВ:\n" +
                "-------------------------");
        for (Satellite sat : constellation.getSatellites()) {
            sat.activate();
        }
    }
}