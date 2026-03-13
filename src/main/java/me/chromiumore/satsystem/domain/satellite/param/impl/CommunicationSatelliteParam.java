package me.chromiumore.satsystem.domain.satellite.param.impl;

import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteType;

@Getter
@Setter
public class CommunicationSatelliteParam extends SatelliteParam {
    private double bandwidth;

    public CommunicationSatelliteParam(String name, double batteryLevel, double bandwidth) {
        super(SatelliteType.COMMUNICATION, name, batteryLevel);
        this.bandwidth = bandwidth;
    }
}
