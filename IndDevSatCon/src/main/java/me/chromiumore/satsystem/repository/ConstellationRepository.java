package me.chromiumore.satsystem.repository;

import me.chromiumore.satsystem.domain.constellation.SatelliteConstellation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ConstellationRepository extends JpaRepository<SatelliteConstellation, Long> {
    Optional<SatelliteConstellation> findByConstellationName(String name);
    boolean existsByConstellationName(String name);
    void deleteByConstellationName(String name);
}
