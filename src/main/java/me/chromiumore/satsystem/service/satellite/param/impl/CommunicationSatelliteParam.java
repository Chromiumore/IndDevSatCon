package me.chromiumore.satsystem.service.satellite.param.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.chromiumore.satsystem.service.satellite.param.SatelliteParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationSatelliteParam extends SatelliteParam {
    private double bandwidth;
}
