package me.chromiumore.satsystem.factory;

import me.chromiumore.satsystem.domain.satellite.CommunicationSatellite;
import me.chromiumore.satsystem.domain.satellite.ImagingSatellite;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.factory.impl.CommunicationSatelliteFactory;
import me.chromiumore.satsystem.factory.impl.ImagingSatelliteFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты фабрик спутников")
public class SatelliteFactoryUnitTest {
    private static CommunicationSatelliteFactory communicationFactory;
    private static ImagingSatelliteFactory imagingFactory;

    @BeforeAll
    static void setUp() {
        communicationFactory = new CommunicationSatelliteFactory();
        imagingFactory = new ImagingSatelliteFactory();
    }

    @Test
    @DisplayName("Создание спутников с параметрами по умолчанию")
    void communicationFactoryCreatingWithDefaultParameters() {
        String name = "Com-1";
        double batteryLevel = 0.75;

        Satellite satellite = communicationFactory.createSatellite(name, batteryLevel);

        assertNotNull(satellite);
        assertInstanceOf(CommunicationSatellite.class, satellite);
        assertEquals(name, satellite.getName());
        assertEquals(batteryLevel, satellite.getBatteryLevel(), 0.01);

        CommunicationSatellite comSatellite = (CommunicationSatellite) satellite;
        assertEquals(CommunicationSatelliteFactory.DEFAULT_BANDWIDTH, comSatellite.getBandwidth());
    }

    @Test
    @DisplayName("Создание спутников с параметрами по умолчанию")
    void communicationFactoryCreatingWithParametersValues() {
        String name = "Com-1";
        double batteryLevel = 0.75;
        double bandwidth = 2000;

        Satellite satellite = communicationFactory.createSatelliteWithParameter(name, batteryLevel, bandwidth);

        assertNotNull(satellite);
        assertInstanceOf(CommunicationSatellite.class, satellite);
        assertEquals(name, satellite.getName());
        assertEquals(batteryLevel, satellite.getBatteryLevel(), 0.01);

        CommunicationSatellite comSatellite = (CommunicationSatellite) satellite;
        assertEquals(bandwidth, comSatellite.getBandwidth());
    }

    @Test
    @DisplayName("Создание спутников с параметрами по умолчанию")
    void imagingFactoryCreatingWithDefaultParameters() {
        String name = "Img-1";
        double batteryLevel = 0.75;

        Satellite satellite = imagingFactory.createSatellite(name, batteryLevel);

        assertNotNull(satellite);
        assertInstanceOf(ImagingSatellite.class, satellite);
        assertEquals(name, satellite.getName());
        assertEquals(batteryLevel, satellite.getBatteryLevel(), 0.01);

        ImagingSatellite imgSatellite = (ImagingSatellite) satellite;
        assertEquals(ImagingSatelliteFactory.DEFAULT_RESOLUTION, imgSatellite.getResolution());
    }

    @Test
    @DisplayName("Создание спутников с параметрами по умолчанию")
    void imagingFactoryCreatingWithParametersValues() {
        String name = "Img-1";
        double batteryLevel = 0.75;
        double resolution = 3;

        Satellite satellite = imagingFactory.createSatelliteWithParameter(name, batteryLevel, resolution);

        assertNotNull(satellite);
        assertInstanceOf(ImagingSatellite.class, satellite);
        assertEquals(name, satellite.getName());
        assertEquals(batteryLevel, satellite.getBatteryLevel(), 0.01);

        ImagingSatellite imgSatellite = (ImagingSatellite) satellite;
        assertEquals(resolution, imgSatellite.getResolution());
    }

    @Test
    @DisplayName("Активация созданных спутников")
    void factoryCreatedSatellitesCanBeActivated() {
        Satellite comSat = communicationFactory.createSatellite("com-1", 0.7);
        Satellite imgSat = imagingFactory.createSatellite("img-1", 0.21);

        assertFalse(comSat.isActive());
        assertTrue(comSat.activate());
        assertTrue(comSat.isActive());

        assertFalse(imgSat.isActive());
        assertTrue(imgSat.activate());
        assertTrue(imgSat.isActive());
    }

    @Test
    @DisplayName("Спутники с низким зарядом не могут быть активированы")
    void factoryCreatedSatellitesCannotBeActivated() {
        Satellite comSat = communicationFactory.createSatellite("com-1", 0);
        Satellite imgSat = imagingFactory.createSatellite("img-1", 0.2);

        assertFalse(comSat.isActive());
        assertFalse(comSat.activate());
        assertFalse(comSat.isActive());

        assertFalse(imgSat.isActive());
        assertFalse(imgSat.activate());
        assertFalse(imgSat.isActive());
    }
}
