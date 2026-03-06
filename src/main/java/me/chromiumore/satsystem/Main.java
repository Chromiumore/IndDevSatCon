package me.chromiumore.satsystem;

import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.repository.ConstellationRepository;
import me.chromiumore.satsystem.service.SpaceOperationCenterService;
import me.chromiumore.satsystem.service.satellite.impl.SatelliteService;
import me.chromiumore.satsystem.param.satellite.SatelliteType;
import me.chromiumore.satsystem.param.satellite.impl.CommunicationSatelliteParam;
import me.chromiumore.satsystem.param.satellite.impl.ImagingSatelliteParam;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("ЗАПУСК СИСТЕМЫ УПРАВЛЕНИЯ СПУТНИКОВОЙ ГРУППИРОВКОЙ\n" +
                "============================================================");

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        ConstellationRepository constellationRepository = context.getBean(ConstellationRepository.class);
        SpaceOperationCenterService operationCenter = context.getBean(SpaceOperationCenterService.class);
        SatelliteService satelliteService = context.getBean(SatelliteService.class);

        System.out.println("\nСОЗДАНИЕ СПЕЦИАЛИЗИРОВАННЫХ СПУТНИКОВ:\n" +
                "---------------------------------------------");

        Satellite com1 = satelliteService.createSatellite(
                new CommunicationSatelliteParam(
                        SatelliteType.COMMUNICATION,
                        "Связь-1",
                        0.85,
                        500)
        );
        Satellite com2 = satelliteService.createSatellite(
                new CommunicationSatelliteParam(
                        SatelliteType.COMMUNICATION,
                        "Связь-2",
                        0.75,
                        1000
                )
        );
        Satellite img1 = satelliteService.createSatellite(
                new ImagingSatelliteParam(
                        SatelliteType.IMAGE,
                        "ДЗЗ-1",
                        0.92,
                        2.5)
        );
        Satellite img2 = satelliteService.createSatellite(
                new ImagingSatelliteParam(
                        SatelliteType.IMAGE,
                        "ДЗЗ-2",
                        0.45,
                        1
                )
        );
        Satellite img3 = satelliteService.createSatellite(
                new ImagingSatelliteParam(
                        SatelliteType.IMAGE,
                        "ДЗЗ-3",
                        0.15,
                        0.5
                )
        );

        System.out.println("---------------------------------------------");


        operationCenter.createAndSaveConstellation("Орбита-1");
        operationCenter.createAndSaveConstellation("Орбита-2");


        System.out.println("\nФОРМИРОВАНИЕ ГРУППИРОВКИ:\n" +
                "-----------------------------------");

        operationCenter.addSatelliteToConstellation("Орбита-1", com1);
        operationCenter.addSatelliteToConstellation("Орбита-1", img1);
        operationCenter.addSatelliteToConstellation("Орбита-2", img2);
        operationCenter.addSatelliteToConstellation("Орбита-2", com2);
        operationCenter.addSatelliteToConstellation("Орбита-2", img3);

        System.out.println("-----------------------------------");


        operationCenter.showConstellationStatus("Орбита-1");
        operationCenter.showConstellationStatus("Орбита-2");

        System.out.println("-----------------------------------");


        System.out.println("\nАКТИВАЦИЯ СПУТНИКОВ:\n-------------------------");
        operationCenter.activateAllSatellites("Орбита-1");
        operationCenter.activateAllSatellites("Орбита-2");

        operationCenter.executeConstellationMission("Орбита-1");
        operationCenter.executeConstellationMission("Орбита-2");

        operationCenter.showConstellationStatus("Орбита-1");
        operationCenter.showConstellationStatus("Орбита-2");
    }
}