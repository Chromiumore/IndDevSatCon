package me.chromiumore.satsystem;

import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import me.chromiumore.satsystem.domain.satellite.CommunicationSatellite;
import me.chromiumore.satsystem.domain.satellite.ImagingSatellite;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.repository.ConstellationRepository;
import me.chromiumore.satsystem.service.ConstellationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Тест сервиса группировок")
public class ConstellationServiceIntegrationTest {
    private static final String CONSTELLATION_1 = "test1";
    private static final String CONSTELLATION_2 = "test2";

    @Autowired
    ConstellationRepository repository;
    @Autowired
    ConstellationService operationCenter;

    @BeforeEach
    void setup() {
        repository.getAll().clear();
    }

    @Test
    @DisplayName("Создание группировок")
    void testCreateConstellations() {
        operationCenter.createAndSaveConstellation(CONSTELLATION_1);
        operationCenter.createAndSaveConstellation(CONSTELLATION_2);

        assertEquals(2, repository.getAll().size());
        assertTrue(repository.contains(CONSTELLATION_1));
        assertTrue(repository.contains(CONSTELLATION_2));
    }

    @Test
    @DisplayName("Добавление спутников в группировку")
    void testAddSatelliteToConstellation() {
        operationCenter.createAndSaveConstellation(CONSTELLATION_1);

        CommunicationSatellite commSat = new CommunicationSatellite("com1", 0.9, 100.0);
        ImagingSatellite imgSat = new ImagingSatellite("img1", 0.8, 0.5);

        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, commSat);
        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, imgSat);

        SatelliteConstellation constellation = repository.get(CONSTELLATION_1);
        assertEquals(2, constellation.getSatellites().size());
    }

    @Test
    @DisplayName("Активация всех спутников в группировке")
    void testActivateAllSatellites() {
        operationCenter.createAndSaveConstellation(CONSTELLATION_1);

        Satellite sat1 = new CommunicationSatellite("com1", 0.9, 100.0);
        Satellite sat2 = new ImagingSatellite("img1", 0.8, 0.5);
        Satellite sat3 = new ImagingSatellite("img2", 0.8, 0.5);
        sat3.activate();

        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, sat1);
        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, sat2);

        operationCenter.activateAllSatellites(CONSTELLATION_1);

        SatelliteConstellation constellation = repository.get(CONSTELLATION_1);
        for (Satellite sat : constellation.getSatellites()) {
            assertTrue(sat.getStatus().contains("Активен") || sat.getStatus().contains("Недостаточно"));
        }
    }

    @Test
    @DisplayName("Выполнение миссий группировки")
    void testExecuteConstellationMission() {
        operationCenter.createAndSaveConstellation(CONSTELLATION_1);

        CommunicationSatellite sat1 = new CommunicationSatellite("com1", 0.9, 100.0);
        ImagingSatellite sat2 = new ImagingSatellite("img1", 0.8, 0.5);

        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, sat1);
        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, sat2);

        int photos = sat2.getPhotosTaken();

        operationCenter.activateAllSatellites(CONSTELLATION_1);
        operationCenter.executeConstellationMission(CONSTELLATION_1);

        SatelliteConstellation constellation = repository.get(CONSTELLATION_1);
        assertEquals(2, constellation.getSatellites().size());
        assertEquals(photos + 1, sat2.getPhotosTaken());
    }

    @Test
    @DisplayName("Просмотр статуса группировки")
    void testShowConstellationStatus() {
        operationCenter.createAndSaveConstellation(CONSTELLATION_1);

        Satellite sat1 = new CommunicationSatellite("com1", 0.9, 100.0);
        operationCenter.addSatelliteToConstellation(CONSTELLATION_1, sat1);

        assertDoesNotThrow(() -> operationCenter.showConstellationStatus(CONSTELLATION_1));
    }
}
