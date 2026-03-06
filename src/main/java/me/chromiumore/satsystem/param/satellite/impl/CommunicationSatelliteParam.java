package me.chromiumore.satsystem.param.satellite.impl;

import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.param.satellite.SatelliteParam;
import me.chromiumore.satsystem.param.satellite.SatelliteType;

@Getter
@Setter
public class CommunicationSatelliteParam extends SatelliteParam {
    private double bandwidth;

    public CommunicationSatelliteParam(SatelliteType type, String name, double batteryLevel, double bandwidth) {
        super(type, name, batteryLevel);
        this.bandwidth = bandwidth;
    }
}
