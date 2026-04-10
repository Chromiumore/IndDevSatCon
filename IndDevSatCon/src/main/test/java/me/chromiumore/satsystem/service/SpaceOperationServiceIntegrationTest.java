package me.chromiumore.satsystem.service;

import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import me.chromiumore.satsystem.domain.request.AddSatelliteRequest;
import me.chromiumore.satsystem.domain.request.MissionRequest;
import me.chromiumore.satsystem.domain.request.MissionTargetType;
import me.chromiumore.satsystem.domain.request.StatusRequest;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.satellite.param.impl.CommunicationSatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.impl.ImagingSatelliteParam;
import me.chromiumore.satsystem.repository.ConstellationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@DisplayName("Тест космического центра")
public class SpaceOperationServiceIntegrationTest {
    @Autowired
    private SpaceOperationCenterService spaceOperationCenterService;
    @Autowired
    private ConstellationRepository constellationRepository;

    @Test
    @DisplayName("Добавление спутников в группировку через фасад")
    void addSatelliteTest() {
        String constellationName = "TestConstellation";
        String comSatName = "Com-1";
        String imgSatName = "Img-1";

        CommunicationSatelliteParam comParam = new CommunicationSatelliteParam(comSatName, 0.9, 500);
        ImagingSatelliteParam imgParam = new ImagingSatelliteParam(imgSatName, 0.8, 2.5);
        AddSatelliteRequest request = new AddSatelliteRequest(
                constellationName,
                List.of(comParam, imgParam)
        );

        spaceOperationCenterService.addSatellite(request);

        SatelliteConstellation constellation = constellationRepository.get(constellationName);
        assertNotNull(constellation);
        assertEquals(2, constellation.getSatellites().size());

        List<String> satelliteNames = constellation.getSatellites().stream()
                .map(Satellite::getName)
                .toList();
        assertTrue(satelliteNames.contains(comSatName));
        assertTrue(satelliteNames.contains(imgSatName));
    }

    @Test
    @DisplayName("Выполнение миссии для всей группировки")
    void executeConstellationMissionTest() {
        String constellationName = "MissionConstellation";
        String satName = "Sat-1";

        CommunicationSatelliteParam comParam = new CommunicationSatelliteParam(satName, 0.9, 500);
        AddSatelliteRequest addRequest = new AddSatelliteRequest(
                constellationName,
                List.of(comParam)
        );
        spaceOperationCenterService.addSatellite(addRequest);

        MissionRequest missionRequest = new MissionRequest(
                MissionTargetType.CONSTELLATION,
                constellationName,
                null
        );
        spaceOperationCenterService.executeMission(missionRequest);

        SatelliteConstellation constellation = constellationRepository.get(constellationName);
        Satellite satellite = constellation.getSatellites().getFirst();
        assertTrue(satellite.isActive());
    }

    @Test
    @DisplayName("Получение системной сводки")
    void getSystemOverviewTest() {
        String constellationName = "OverviewConst";
        String satName = "OverviewSat";

        CommunicationSatelliteParam comParam = new CommunicationSatelliteParam(satName, 0.7, 500);
        AddSatelliteRequest addRequest = new AddSatelliteRequest(
                constellationName,
                List.of(comParam)
        );
        spaceOperationCenterService.addSatellite(addRequest);

        String overview = spaceOperationCenterService.getSystemOverview();
        System.out.println(overview);

        assertTrue(overview.contains(constellationName));
        assertTrue(overview.contains(satName));
        assertTrue(overview.contains("заряд: 70%") || overview.contains("заряд: 70"));
    }

    @Test
    @DisplayName("Получение статуса спутника")
    void getSatelliteStatus() {
        String constellationName = "StatusConst";
        String satName = "StatusSat";

        CommunicationSatelliteParam comParam = new CommunicationSatelliteParam(satName, 0.7, 500);
        StatusRequest statusRequest = new StatusRequest(
                constellationName,
                satName
        );
        AddSatelliteRequest addRequest = new AddSatelliteRequest(
                constellationName,
                List.of(comParam)
        );

        spaceOperationCenterService.addSatellite(addRequest);

        String statusMessage = spaceOperationCenterService.getSatelliteStatus(statusRequest);
        assertEquals("Не активирован", statusMessage); // Захардкодил фуу!!!

        MissionRequest missionRequest = new MissionRequest(
                MissionTargetType.CONSTELLATION,
                constellationName,
                null
        );
        spaceOperationCenterService.executeMission(missionRequest);

        statusMessage = spaceOperationCenterService.getSatelliteStatus(statusRequest);
        assertEquals("Активен", statusMessage);
    }

}
