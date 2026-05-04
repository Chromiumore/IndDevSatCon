package me.chromiumore.satsystem.controller;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.domain.request.EnergySystemUpdateRequest;
import me.chromiumore.satsystem.domain.satellite.EnergySystem;
import me.chromiumore.satsystem.service.EnergySystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energy-systems")
@RequiredArgsConstructor
public class EnergySystemController {
    private final EnergySystemService energySystemService;

    @GetMapping
    public List<EnergySystem> getAllEnergySystems() {
        return energySystemService.getAllEnergySystems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergySystem> getEnergySystemById(@PathVariable("id") Long id) {
        return energySystemService.getEnergySystemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public ResponseEntity<EnergySystem> updateEnergySystem(@PathVariable("id") Long id,
                                                           @RequestBody EnergySystemUpdateRequest request) {
        EnergySystem updated = energySystemService.updateEnergySystem(id, request);
        return ResponseEntity.ok(updated);
    }
}
