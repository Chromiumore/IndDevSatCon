package me.chromiumore.satsystem.service;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.aop.LogExecutionTime;
import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import me.chromiumore.satsystem.domain.request.CreateAndAddSatelliteRequest;
import me.chromiumore.satsystem.domain.request.StatusRequest;
import me.chromiumore.satsystem.domain.request.MissionRequest;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.repository.SatelliteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SpaceOperationCenterService {
    private final ConstellationService constellationService;
    private final SatelliteService satelliteService;
    private final SatelliteRepository satelliteRepository;

    @LogExecutionTime
    public void addSatellite(CreateAndAddSatelliteRequest request) {
        try {
            constellationService.showConstellationStatus(request.constellationName());
        } catch (Exception e) {
            constellationService.createAndSaveConstellation(request.constellationName());
        }

        SatelliteConstellation constellation = constellationService.getConstellationByName(
                request.constellationName());

        for (SatelliteParam param : request.satelliteParams()) {
            Satellite satellite = satelliteService.createAndSaveSatellite(param);
            constellationService.addSatelliteToConstellation(constellation.getId(), satellite.getId());
        }
    }

    public void removeSatelliteFromConstellation(
            String constellationName,
            String satelliteName
    ) {
        constellationService.removeSatelliteFromConstellation(constellationName, satelliteName);
    }

    public void executeMission(MissionRequest request) {
        switch (request.targetType()) {
            case CONSTELLATION -> {
                constellationService.activateAllSatellites(request.constellationName());
                constellationService.executeConstellationMission(request.constellationName());
            }
            case SINGLE_SATELLITE -> {
                SatelliteConstellation constellation = constellationService.getConstellationByName(request.constellationName());
                var satellite = constellation.getSatellites().stream()
                        .filter(s -> s.getName().equals(request.satelliteName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Спутник не найден: " + request.satelliteName()));
                satellite.activate();
                satellite.performMission();
                satelliteRepository.save(satellite);
            }
        }
    }

    public String getSystemOverview() {
        List<SatelliteConstellation> allConstellation = constellationService.getAllConstellations();
        StringBuilder sb = new StringBuilder("=== Системная сводка ===\n");
        sb.append("Всего группировок: ").append(allConstellation.size()).append("\n");
        allConstellation.forEach(cons -> {
            sb.append("Группировка '").append(cons.getConstellationName())
                    .append("' : спутников ").append(cons.getSatellites().size()).append("\n");
            cons.getSatellites().forEach(sat -> {
                sb.append("    - ").append(sat.getName())
                        .append(" [").append(sat.isActive() ? "Активен" : "Неактивен")
                        .append("], заряд: ").append((int)(sat.getBatteryLevel() * 100)).append("%\n");
            });
        });

        return sb.toString();
    }

    public String getSatelliteStatus(StatusRequest request) {
        SatelliteConstellation constellation = constellationService.getConstellationByName(request.constellationName());
        Satellite satellite = constellation.getSatellites().stream()
                .filter(s -> s.getName().equals(request.satelliteName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Спутник не найден: " + request.satelliteName()));

        return satellite.getStatus();
    }
}
