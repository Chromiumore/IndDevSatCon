package me.chromiumore.satsystem.model.satellite.factory;

import me.chromiumore.satsystem.model.satellite.Satellite;

public abstract class SatelliteFactory {
    public abstract Satellite createSatellite(String name, double batteryLevel);
    public abstract Satellite createSatelliteWithParameter(String name, double batteryLevel, double parameter);
}
