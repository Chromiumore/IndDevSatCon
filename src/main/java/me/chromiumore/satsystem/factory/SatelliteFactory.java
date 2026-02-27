package me.chromiumore.satsystem.factory;

import me.chromiumore.satsystem.domain.satellite.Satellite;

public interface SatelliteFactory {
    Satellite createSatellite(String name, double batteryLevel);
    Satellite createSatelliteWithParameter(String name, double batteryLevel, double parameter);
}
