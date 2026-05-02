package me.chromiumore.satsystem.controller;

import lombok.RequiredArgsConstructor;
import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import me.chromiumore.satsystem.domain.satellite.Satellite;
import me.chromiumore.satsystem.service.ConstellationService;
import me.chromiumore.satsystem.service.SatelliteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/constellations")
@RequiredArgsConstructor
public class ConstellationController {
    private final ConstellationService constellationService;

    public record CreateConstellationRequest(String name) {}
    public record AddSatelliteRequest(Long satelliteId) {}

    @PostMapping
    public ResponseEntity<SatelliteConstellation> createConstellation(@RequestBody CreateConstellationRequest request) {
        SatelliteConstellation constellation = constellationService.createAndSaveConstellation(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(constellation);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> addSatelliteToConstellation(
            @PathVariable Long constellationId,
            @PathVariable Long satelliteId) {
        constellationService.addSatelliteToConstellation(constellationId, satelliteId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public SatelliteConstellation getConstellationByName(@PathVariable String constellationName) {
        return constellationService.getConstellationByName(constellationName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SatelliteConstellation> getConstellationById(@PathVariable Long id) {
        return constellationService.getConstellationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<SatelliteConstellation> getAllConstellations() {
        return constellationService.getAllConstellations();
    }
}
