package me.chromiumore.satsystem.service;

import me.chromiumore.satsystem.repository.ConstellationRepository;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import me.chromiumore.satsystem.repository.SatelliteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final SatelliteRepository satelliteRepository;

    public ConstellationService(ConstellationRepository constellationRepository,
                                SatelliteRepository satelliteRepository) {
        this.constellationRepository = constellationRepository;
        this.satelliteRepository = satelliteRepository;
    }

    public SatelliteConstellation createAndSaveConstellation(String name) {
        SatelliteConstellation constellation = new SatelliteConstellation(name);
        constellationRepository.save(constellation);
    }

    public void addSatelliteToConstellation(Long constellationId, Long satelliteId) {
        SatelliteConstellation constellation = constellationRepository.findById(constellationId)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена"));
        Satellite satellite = satelliteRepository.findById(satelliteId)
                        .orElseThrow(() -> new RuntimeException("Спутник не найден"));

        constellation.addSatellite(satellite);
        satellite.setConstellation(constellation);
        satelliteRepository.save(satellite);
    }

    @Transactional(readOnly = true)
    public SatelliteConstellation getConstellationByName(String constellationName) {
        return constellationRepository.findByConstellationName(constellationName)
                .orElseThrow(() -> new RuntimeException("Группировка не найдена: " + constellationName));
    }

    @Transactional(readOnly = true)
    public Optional<SatelliteConstellation> getConstellationById(Long id) {
        return constellationRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<SatelliteConstellation> getAllConstellations() {
        return constellationRepository.findAll();
    }

    public void deleteConstellation(Long id) {
        if (!constellationRepository.existsById(id)) {
            throw new RuntimeException("Группирока не найдена: " + id);
        }
        constellationRepository.deleteById(id);
    }

    public void removeSatelliteFromConstellation(String constellationName, String satelliteName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        Satellite satellite = constellation.getSatellites().stream()
                        .filter(sat -> sat.getName().equals(satelliteName))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Спутник не найден"));
        constellation.removeSatellite(satellite.getName());
        satelliteRepository.delete(satellite);
    }

    public void executeConstellationMission(String constellationName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        System.out.println("\n=== ВЫПОЛНЕНИЕ МИССИЙ ДЛЯ ГРУППИРОВКИ: " + constellationName   + " ===");
        constellation.executeAllMissions();
    }

    public void activateAllSatellites(String constellationName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        System.out.println("\n=== АКТИВАЦИЯ СПУТНИКОВ В ГРУППИРОВКЕ " + constellationName + " ===");

        for (Satellite satellite : constellation.getSatellites()) {
            satellite.activate();
        }
    }

    public void showConstellationStatus(String constellationName) {
        SatelliteConstellation constellation = getConstellationByName(constellationName);
        System.out.println("\n=== СТАТУС ГРУППИРОВКИ: " + constellationName + " ===");
        System.out.println("Количество спутников: " + constellation.getSatellites().size());

        for (Satellite satellite : constellation.getSatellites()) {
            System.out.println(satellite);
        }
    }
}
