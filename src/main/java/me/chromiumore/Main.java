package me.chromiumore;

public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ\n" +
                "============================================================");


        System.out.println("СОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:\n" +
                "---------------------------------------------");

        Satellite sat1 = new CommunicationSatellite("1", 0.85, 500);
    }
}