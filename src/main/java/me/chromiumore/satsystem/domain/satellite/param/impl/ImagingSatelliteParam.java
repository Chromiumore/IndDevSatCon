package me.chromiumore.satsystem.domain.satellite.param.impl;

import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteType;

@Getter
@Setter
public class ImagingSatelliteParam extends SatelliteParam {
    private double resolution;

    public ImagingSatelliteParam(String name, double batteryLevel, double resolution) {
        super(SatelliteType.IMAGE, name, batteryLevel);
        this.resolution = resolution;
    }

    public ImagingSatelliteParam() {
        super(SatelliteType.IMAGE, null, 0.0);
    }
}
