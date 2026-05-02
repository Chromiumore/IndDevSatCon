package me.chromiumore.satsystem.service;

import lombok.AllArgsConstructor;
import me.chromiumore.satsystem.domain.request.EnergySystemUpdateRequest;
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

    public EnergySystem updateEnergySystem(Long id, EnergySystemUpdateRequest request) {
        EnergySystem energy = energySystemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Энергосистема не найдена: " + id));

        if (request.batteryLevel() != null) energy.setBatteryLevel(request.batteryLevel());
        if (request.lowBatteryThreshold() != null) energy.setBatteryLevel(request.lowBatteryThreshold());
        if (request.minBattery() != null) energy.setBatteryLevel(request.minBattery());
        if (request.maxBattery() != null) energy.setBatteryLevel(request.maxBattery());

        return energySystemRepository.save(energy);
    }
}
