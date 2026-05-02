package me.chromiumore.satsystem.service;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.exception.SpaceOperationException;
import me.chromiumore.satsystem.factory.SatelliteFactory;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.repository.SatelliteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SatelliteService {
    private final SatelliteRepository satelliteRepository;
    private final List<SatelliteFactory> satelliteFactories;

    private Satellite createSatellite(SatelliteParam param) throws SpaceOperationException {
        for (SatelliteFactory factory : satelliteFactories) {
            if (factory.isSatelliteTypeSupported(param.getType())) {
                return factory.createSatelliteWithParameter(param);
            }
        }
        throw new SpaceOperationException("Factory not found");
    }

    public Satellite createAndSaveSatellite(SatelliteParam param) {
        Satellite satellite = createSatellite(param);
        return satelliteRepository.save(satellite);
    }

    @Transactional(readOnly = true)
    public List<Satellite> getAllSatellites() {
        return satelliteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Satellite> getSatelliteById(Long id) {
        return satelliteRepository.findById(id);
    }

    public Satellite updateSatellite(Long id, Satellite updatedSatellite) {
        if (!satelliteRepository.existsById(id)) {
            throw new RuntimeException("Спутник не найден: " + id);
        }
        updatedSatellite.setId(id);
        return satelliteRepository.save(updatedSatellite);
    }

    public void deleteSatellite(Long id) {
        if (!satelliteRepository.existsById(id)) {
            throw new RuntimeException("Спутник не найден: " + id);
        }
        satelliteRepository.deleteById(id);
    }
}
