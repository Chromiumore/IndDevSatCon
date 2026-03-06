package me.chromiumore.satsystem.domain.satellite.param;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class SatelliteParam {
    protected SatelliteType type;
    protected String name;
    protected double batteryLevel;

    public SatelliteParam(SatelliteType type, String name, double batteryLevel) {
        this.type = type;
        this.name = name;
        this.batteryLevel = batteryLevel;
    }
}
