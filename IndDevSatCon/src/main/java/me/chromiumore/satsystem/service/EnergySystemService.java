package me.chromiumore.satsystem.service;

import lombok.AllArgsConstructor;
import me.chromiumore.satsystem.domain.satellite.EnergySystem;
import me.chromiumore.satsystem.repository.EnergySystemRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EnergySystemService {
    private final EnergySystemRepository energySystemRepository;

    @Transactional(readOnly = true)
    public List<EnergySystem> getAllEnergySystems() {
        return energySystemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EnergySystem> getEnergySystemById(Long id) {
        return energySystemRepository.findById(id);
    }
}
