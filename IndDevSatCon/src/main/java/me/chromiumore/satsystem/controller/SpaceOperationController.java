package me.chromiumore.satsystem.controller;

import lombok.AllArgsConstructor;
import me.chromiumore.satsystem.domain.request.AddSatelliteRequest;
import me.chromiumore.satsystem.domain.request.MissionRequest;
import me.chromiumore.satsystem.service.SpaceOperationCenterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/space-operation")
@AllArgsConstructor
public class SpaceOperationController {
    private final SpaceOperationCenterService spaceOperationCenterService;

    @PostMapping("/add-satellites")
    public ResponseEntity<Void> addSatellites(@RequestBody AddSatelliteRequest request) {
        spaceOperationCenterService.addSatellite(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/missions")
    public ResponseEntity<Void> executeMission(@RequestBody MissionRequest request) {
        spaceOperationCenterService.executeMission(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/overview")
    public ResponseEntity<String> getOverview() {
        String result = spaceOperationCenterService.getSystemOverview();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/constellations/{constellationName}/satellites/{satelliteName}")
    public ResponseEntity<Void> removeSatelliteFromConstellation(
            @PathVariable("constellationName") String constellationName,
            @PathVariable("satelliteName") String satelliteName
    ) {
        spaceOperationCenterService.removeSatelliteFromConstellation(
                constellationName,
                satelliteName
        );

        return ResponseEntity.noContent().build();
    }
}
