package me.chromiumore.satsystem.domain.creation.request;

import me.chromiumore.satsystem.domain.creation.param.SatelliteParam;

import java.util.List;

public record AddSatelliteRequest(String constellationName, List<SatelliteParam> satelliteParams) {
}
