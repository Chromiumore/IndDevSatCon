package me.chromiumore.satsystem.service.satellite.param.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chromiumore.satsystem.service.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.service.satellite.param.SatelliteType;

@Getter
@Setter
public class ImagingSatelliteParam extends SatelliteParam {
    private double resolution;

    public ImagingSatelliteParam(SatelliteType type, String name, double batteryLevel, double resolution) {
        super(type, name, batteryLevel);
        this.resolution = resolution;
    }
}
