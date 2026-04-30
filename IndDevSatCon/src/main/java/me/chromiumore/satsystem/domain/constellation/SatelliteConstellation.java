package me.chromiumore.satsystem.domain.constellation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.chromiumore.satsystem.domain.satellite.Satellite;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "satellite_constellation")
@Getter
@NoArgsConstructor
public class SatelliteConstellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String constellationName;

    @JsonManagedReference
    @OneToMany(mappedBy = "constellation", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Satellite> satellites = new ArrayList<>();

    public SatelliteConstellation(String constellationName) {
        this.constellationName = constellationName;
        System.out.printf("Создана спутниковая группировка: %s\n", constellationName);
    }

    public void addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
        System.out.printf("%s добавлен в группировку '%s'\n", satellite.getName(), constellationName);
    }

    public Satellite getByName(String satelliteName) {
        return satellites.stream()
                .filter(sat -> satelliteName.equals(sat.getName()))
                .findFirst()
                .orElse(null);
    }

    public void executeAllMissions() {
        System.out.println("ВЫПОЛНЕНИЕ МИССИЙ ГРУППИРОВКИ " + constellationName + "\n" +
                "==================================================");
        for (Satellite sat : satellites) {
            sat.performMission();
        }
    }

    public void removeSatellite(String satelliteName) {
        Satellite satellite = getByName(satelliteName);
        satellites.remove(satellite);
    }
}
