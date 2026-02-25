package me.chromiumore.satsystem.service;

import me.chromiumore.satsystem.repository.ConstellationRepository;
import me.chromiumore.satsystem.model.satellite.satellite.Satellite;
import me.chromiumore.satsystem.model.satellite.constellation.SatelliteConstellation;
import org.springframework.stereotype.Service;

@Service
public class SpaceOperationCenterService {
    private final ConstellationRepository repository;

    public SpaceOperationCenterService(ConstellationRepository repository) {
        this.repository = repository;
    }

    public void createAndSaveConstellation(String name) {
        SatelliteConstellation constellation = new SatelliteConstellation(name);
        repository.add(constellation);
    }

    public void addSatelliteToConstellation(String constellationName, Satellite satellite) {
        SatelliteConstellation constellation = repository.get(constellationName);
        constellation.addSatellite(satellite);
        System.out.println("Добавлен спутник " + satellite.getName() +
                " в группировку " + constellationName);
    }

    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = repository.get(constellationName);
        System.out.println("\n=== ВЫПОЛНЕНИЕ МИССИЙ ДЛЯ ГРУППИРОВКИ: " + constellationName   + " ===");
        constellation.executeAllMissions();
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = repository.get(constellationName);
        System.out.println("\n=== АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ " + constellationName + " ===");

        for (Satellite satellite : constellation.getSatellites()) {
            satellite.activate();
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = repository.get(constellationName);
        System.out.println("\n=== СТАТУС ГРУППИРОВКИ: " + constellationName + " ===");
        System.out.println("Количество спутников: " + constellation.getSatellites().size());

        for (Satellite satellite : constellation.getSatellites()) {
            System.out.println(satellite);
        }
    }
}
