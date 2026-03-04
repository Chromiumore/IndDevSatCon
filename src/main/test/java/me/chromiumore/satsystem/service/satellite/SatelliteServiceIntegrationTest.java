package me.chromiumore.satsystem.service.satellite;

import me.chromiumore.satsystem.domain.satellite.CommunicationSatellite;
import me.chromiumore.satsystem.domain.satellite.ImagingSatellite;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.service.satellite.impl.SatelliteService;
import me.chromiumore.satsystem.service.satellite.param.SatelliteType;
import me.chromiumore.satsystem.service.satellite.param.impl.CommunicationSatelliteParam;
import me.chromiumore.satsystem.service.satellite.param.impl.ImagingSatelliteParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Тест спутникового сервиса")
public class SatelliteServiceIntegrationTest {
    @Autowired
    SatelliteService service;

    @Test
    @DisplayName("Создание коммуникационных спутников")
    void createCommunicationSatellites() {
        CommunicationSatelliteParam param = new CommunicationSatelliteParam(
                SatelliteType.COMMUNICATION,
                "com-1",
                0.6,
                750
        );
        Satellite sat = service.createSatellite(param);

        assertEquals(param.getName(), sat.getName());
        assertEquals(param.getBatteryLevel(), sat.getBatteryLevel());
        assertInstanceOf(CommunicationSatellite.class, sat);

        CommunicationSatellite comSat = (CommunicationSatellite) sat;

        assertEquals(param.getBandwidth(), comSat.getBandwidth());
    }

    @Test
    @DisplayName("Создание спутников снимков")
    void createImagingSatellites() {
        ImagingSatelliteParam param = new ImagingSatelliteParam(
                SatelliteType.IMAGE,
                "img-1",
                0.5,
                2
        );
        Satellite sat = service.createSatellite(param);

        assertEquals(param.getName(), sat.getName());
        assertEquals(param.getBatteryLevel(), sat.getBatteryLevel());
        assertInstanceOf(ImagingSatellite.class, sat);

        ImagingSatellite imgSat = (ImagingSatellite) sat;

        assertEquals(param.getResolution(), imgSat.getResolution());
    }
}
