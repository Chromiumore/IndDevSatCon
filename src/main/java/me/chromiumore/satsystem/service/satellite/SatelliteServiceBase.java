package me.chromiumore.satsystem.service.satellite;

import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.param.satellite.SatelliteParam;

public interface SatelliteServiceBase {
    Satellite createSatellite(SatelliteParam param);
}
