package me.chromiumore.satsystem.service.satellite.param.impl;

import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.service.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.service.satellite.param.SatelliteType;

@Getter
@Setter
public class CommunicationSatelliteParam extends SatelliteParam {
    private double bandwidth;

    public CommunicationSatelliteParam(SatelliteType type, String name, double batteryLevel, double bandwidth) {
        super(type, name, batteryLevel);
        this.bandwidth = bandwidth;
    }
}
