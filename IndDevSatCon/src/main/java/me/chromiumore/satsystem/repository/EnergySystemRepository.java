package me.chromiumore.satsystem.repository;

import me.chromiumore.satsystem.domain.satellite.EnergySystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergySystemRepository extends JpaRepository<EnergySystem, Long> {
}
