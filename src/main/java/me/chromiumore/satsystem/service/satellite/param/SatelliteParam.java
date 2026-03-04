package me.chromiumore.satsystem.service.satellite.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SatelliteParam {
    protected SatelliteType type;
    protected String name;
    protected double batteryLevel;
}
