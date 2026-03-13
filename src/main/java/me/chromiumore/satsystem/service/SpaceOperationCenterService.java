package me.chromiumore.satsystem.service;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import me.chromiumore.satsystem.domain.request.AddSatelliteRequest;
import me.chromiumore.satsystem.domain.request.StatusRequest;
import me.chromiumore.satsystem.domain.request.MissionRequest;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.service.satellite.impl.SatelliteService;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class SpaceOperationCenterService {
    private final ConstellationService constellationService;
    private final SatelliteService satelliteService;

    public void addSatellite(AddSatelliteRequest request) {
        try {
            constellationService.showConstellationStatus(request.constellationName());
        } catch (Exception e) {
            constellationService.createAndSaveConstellation(request.constellationName());
        }

        for (SatelliteParam param : request.satelliteParams()) {
            Satellite satellite = satelliteService.createSatellite(param);
            constellationService.addSatelliteToConstellation(request.constellationName(), satellite);
        }
    }

    public void executeMission(MissionRequest request) {
        switch (request.targetType()) {
            case CONSTELLATION -> {
                constellationService.activateAllSatellites(request.constellationName());
                constellationService.executeConstellationMission(request.constellationName());
            }
            case SINGLE_SATELLITE -> {
                SatelliteConstellation constellation = constellationService.getConstellation(request.constellationName());
                var satellite = constellation.getSatellites().stream()
                        .filter(s -> s.getName().equals(request.satelliteName()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Спутник не найден: " + request.satelliteName()));
                satellite.activate();
                satellite.performMission();
            }
        }
    }

    public String getSystemOverview() {
        Map<String, SatelliteConstellation> allConstellation = constellationService.getAllConstellations();
        StringBuilder sb = new StringBuilder("=== Системная сводка ===\n");
        sb.append("Всего группировок: ").append(allConstellation.size()).append("\n");
        allConstellation.values().forEach(cons -> {
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
        SatelliteConstellation constellation = constellationService.getConstellation(request.constellationName());
        Satellite satellite = constellation.getSatellites().stream()
                .filter(s -> s.getName().equals(request.satelliteName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Спутник не найден: " + request.satelliteName()));

        return satellite.getStatus();
    }
}
