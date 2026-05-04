package me.chromiumore.satsystem.repository;

import me.chromiumore.satsystem.domain.satellite.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SatelliteRepository extends JpaRepository<Satellite, Long> {
}
