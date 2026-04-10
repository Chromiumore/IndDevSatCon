package me.chromiumore.satsystem.factory;

import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteType;

public interface SatelliteFactory {
    Satellite createSatelliteWithParameter(SatelliteParam param);
    boolean isSatelliteTypeSupported(SatelliteType type);
}
