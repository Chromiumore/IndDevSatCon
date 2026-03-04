package me.chromiumore.satsystem.factory.impl;

import me.chromiumore.satsystem.domain.satellite.CommunicationSatellite;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.exception.SpaceOperationException;
import me.chromiumore.satsystem.factory.SatelliteFactory;
import me.chromiumore.satsystem.service.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.service.satellite.param.SatelliteType;
import me.chromiumore.satsystem.service.satellite.param.impl.CommunicationSatelliteParam;
import org.springframework.stereotype.Component;

@Component
public class CommunicationSatelliteFactory implements SatelliteFactory {
    public static final double DEFAULT_BANDWIDTH = 500;


    @Override
    public Satellite createSatelliteWithParameter(SatelliteParam param) throws SpaceOperationException {
        if (param instanceof CommunicationSatelliteParam communicationParam) {
            return new CommunicationSatellite(
                    communicationParam.getName(),
                    communicationParam.getBatteryLevel(),
                    communicationParam.getBandwidth()
            );
        }
        throw new SpaceOperationException("Parameter is unsupported");
    }

    @Override
    public boolean isSatelliteTypeSupported(SatelliteType type) {
        return type == SatelliteType.COMMUNICATION;
    }
}
