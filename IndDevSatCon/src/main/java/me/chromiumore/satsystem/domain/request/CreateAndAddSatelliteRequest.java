package me.chromiumore.satsystem.domain.request;

import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;

import java.util.List;

public record CreateAndAddSatelliteRequest(String constellationName, List<SatelliteParam> satelliteParams) {
}
