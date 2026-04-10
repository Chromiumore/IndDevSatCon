package me.chromiumore.satsystem.service;

import me.chromiumore.satsystem.repository.ConstellationRepository;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConstellationService {
    private final ConstellationRepository repository;

    public ConstellationService(ConstellationRepository repository) {
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

    public SatelliteConstellation getConstellation(String constellationName) {
        return repository.get(constellationName);
    }

    public Map<String, SatelliteConstellation> getAllConstellations() {
        return repository.getAll();
    }

    public void removeSatelliteFromConstellation(String constellationName, String satelliteName) {
        SatelliteConstellation constellation = repository.get(constellationName);
        constellation.removeSatellite(satelliteName);
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
