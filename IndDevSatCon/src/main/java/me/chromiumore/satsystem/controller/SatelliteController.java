package me.chromiumore.satsystem.controller;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.domain.satellite.param.SatelliteParam;
import me.chromiumore.satsystem.service.SatelliteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/satellites")
@RequiredArgsConstructor
public class SatelliteController {
    private final SatelliteService satelliteService;

    @PostMapping
    public ResponseEntity<Satellite> createSatellite(@RequestBody SatelliteParam param) {
        Satellite satellite = satelliteService.createAndSaveSatellite(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(satellite);
    }

    @GetMapping
    public List<Satellite> getAllSatellites() {
        return satelliteService.getAllSatellites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Satellite> getSatelliteById(@PathVariable("id") Long id) {
        return satelliteService.getSatelliteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Satellite> updatedSatellite(@PathVariable("id") Long id, @RequestBody Satellite satellite) {
        Satellite updated = satelliteService.updateSatellite(id, satellite);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSatellite(@PathVariable("id") Long id) {
        satelliteService.deleteSatellite(id);
        return ResponseEntity.noContent().build();
    }
}
