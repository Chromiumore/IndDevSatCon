package me.chromiumore.satsystem.model.satellite.constellation;

import lombok.Getter;
import me.chromiumore.satsystem.model.satellite.satellite.Satellite;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SatelliteConstellation {
    private String constellationName;
    private List<Satellite> satellites;

    public SatelliteConstellation(String constellationName) {
        this.constellationName = constellationName;
        this.satellites = new ArrayList<>();
        System.out.printf("Создана спутниковая группировка: %s\n", constellationName);
    }

    public void addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
        System.out.printf("%s добавлен в группировку '%s'\n", satellite.getName(), constellationName);
    }

    public void executeAllMissions() {
        System.out.println("ВЫПОЛНЕНИЕ МИССИЙ ГРУППИРОВКИ " + constellationName + "\n" +
                "==================================================");
        for (Satellite sat : satellites) {
            sat.performMission();
        }
    }
}
