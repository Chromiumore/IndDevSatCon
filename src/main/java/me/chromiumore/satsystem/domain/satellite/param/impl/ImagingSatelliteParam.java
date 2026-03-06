package me.chromiumore.satsystem.domain.satellite.param.impl;

import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteType;

@Getter
@Setter
public class ImagingSatelliteParam extends SatelliteParam {
    private double resolution;

    public ImagingSatelliteParam(SatelliteType type, String name, double batteryLevel, double resolution) {
        super(type, name, batteryLevel);
        this.resolution = resolution;
    }
}
