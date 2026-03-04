package me.chromiumore.satsystem.service.satellite.impl;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.exception.SpaceOperationException;
import me.chromiumore.satsystem.factory.SatelliteFactory;
import me.chromiumore.satsystem.service.satellite.SatelliteServiceBase;
import me.chromiumore.satsystem.service.satellite.param.SatelliteParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SatelliteService implements SatelliteServiceBase {
    private final List<SatelliteFactory> factories;

    @Override
    public Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        for (SatelliteFactory factory : factories) {
            if (factory.isSatelliteTypeSupported(param.getType())) {
                return factory.createSatelliteWithParameter(param);
            }
        }
        throw new SpaceOperationException("Factory not found");
    }
}
