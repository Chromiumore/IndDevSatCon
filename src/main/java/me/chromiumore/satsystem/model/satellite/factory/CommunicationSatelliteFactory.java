package me.chromiumore.satsystem.model.satellite.factory;

import me.chromiumore.satsystem.model.satellite.satellite.CommunicationSatellite;

public class CommunicationSatelliteFactory extends SatelliteFactory {
    @Override
    public CommunicationSatellite createSatellite(String name, double batteryLevel) {
        return new CommunicationSatellite(name, batteryLevel, 500);
    }

    @Override
    public CommunicationSatellite createSatelliteWithParameter(String name, double batteryLevel, double parameter) {
        return new CommunicationSatellite(name, batteryLevel, parameter);
    }
}
