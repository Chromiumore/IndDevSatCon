package me.chromiumore.satsystem.factory;

import me.chromiumore.satsystem.domain.satellite.CommunicationSatellite;
import me.chromiumore.satsystem.domain.satellite.ImagingSatellite;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.factory.impl.CommunicationSatelliteFactory;
import me.chromiumore.satsystem.factory.impl.ImagingSatelliteFactory;
import me.chromiumore.satsystem.param.satellite.SatelliteType;
import me.chromiumore.satsystem.param.satellite.impl.CommunicationSatelliteParam;
import me.chromiumore.satsystem.param.satellite.impl.ImagingSatelliteParam;
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
    void communicationFactoryCreatingWithParametersValues() {
        String name = "Com-1";
        double batteryLevel = 0.75;
        double bandwidth = 2000;

        Satellite satellite = communicationFactory.createSatelliteWithParameter(
                new CommunicationSatelliteParam(
                        SatelliteType.COMMUNICATION,
                        name,
                        batteryLevel,
                        bandwidth
                )
        );

        assertNotNull(satellite);
        assertInstanceOf(CommunicationSatellite.class, satellite);
        assertEquals(name, satellite.getName());
        assertEquals(batteryLevel, satellite.getBatteryLevel(), 0.01);

        CommunicationSatellite comSatellite = (CommunicationSatellite) satellite;
        assertEquals(bandwidth, comSatellite.getBandwidth());
    }

    @Test
    @DisplayName("Создание спутников с параметрами по умолчанию")
    void imagingFactoryCreatingWithParametersValues() {
        String name = "Img-1";
        double batteryLevel = 0.75;
        double resolution = 3;

        Satellite satellite = imagingFactory.createSatelliteWithParameter(
                new ImagingSatelliteParam(
                        SatelliteType.IMAGE,
                        name,
                        batteryLevel,
                        resolution
                )
        );

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
        Satellite comSat = communicationFactory.createSatelliteWithParameter(
                new CommunicationSatelliteParam(
                        SatelliteType.COMMUNICATION,
                        "com-1",
                        0.7,
                        500
                )
        );
        Satellite imgSat = imagingFactory.createSatelliteWithParameter(
                new ImagingSatelliteParam(
                        SatelliteType.IMAGE,
                        "img-1",
                        0.21,
                        1
                )
        );

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
        Satellite comSat = communicationFactory.createSatelliteWithParameter(
                new CommunicationSatelliteParam(
                        SatelliteType.COMMUNICATION,
                        "com-1",
                        0,
                        500
                )
        );
        Satellite imgSat = imagingFactory.createSatelliteWithParameter(
                new ImagingSatelliteParam(
                        SatelliteType.IMAGE,
                        "img-1",
                        0.2,
                        1
                )
        );

        assertFalse(comSat.isActive());
        assertFalse(comSat.activate());
        assertFalse(comSat.isActive());

        assertFalse(imgSat.isActive());
        assertFalse(imgSat.activate());
        assertFalse(imgSat.isActive());
    }
}
