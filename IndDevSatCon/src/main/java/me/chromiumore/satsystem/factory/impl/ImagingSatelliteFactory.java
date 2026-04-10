package me.chromiumore.satsystem.factory.impl;

import me.chromiumore.satsystem.domain.satellite.ImagingSatellite;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.exception.SpaceOperationException;
import me.chromiumore.satsystem.factory.SatelliteFactory;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteType;
import me.chromiumore.satsystem.domain.satellite.param.impl.ImagingSatelliteParam;
import org.springframework.stereotype.Component;

@Component
public class ImagingSatelliteFactory implements SatelliteFactory {
    public static final double DEFAULT_RESOLUTION = 1;


    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException {
        if (param instanceof ImagingSatelliteParam imagingParam) {
            return new ImagingSatellite(
                    imagingParam.getName(),
                    imagingParam.getBatteryLevel(),
                    imagingParam.getResolution()
            );
        }
        throw new SpaceOperationException("Parameter is unsupported");
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return SatelliteType.IMAGE.equals(type);
    }
}
