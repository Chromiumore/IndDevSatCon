package me.chromiumore.satsystem.factory;

import me.chromiumore.satsystem.domain.satellite.CommunicationSatellite;
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
}
