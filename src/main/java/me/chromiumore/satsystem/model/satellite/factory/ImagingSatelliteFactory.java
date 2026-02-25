package me.chromiumore.satsystem.model.satellite.factory;

import me.chromiumore.satsystem.model.satellite.satellite.ImagingSatellite;

public class ImagingSatelliteFactory extends SatelliteFactory {
    @Override
    public ImagingSatellite createSatellite(String name, double batteryLevel) {
        return new ImagingSatellite(name, batteryLevel, 1);
    }

    @Override
    public ImagingSatellite createSatelliteWithParameter(String name, double batteryLevel, double parameter) {
        return new ImagingSatellite(name, batteryLevel, parameter);
    }
}
