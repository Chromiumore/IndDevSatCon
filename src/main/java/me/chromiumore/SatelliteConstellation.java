package me.chromiumore;

import java.util.ArrayList;
import java.util.List;

public class SatelliteConstellation {
    private String constellationName;
    private List<Satellite> satellites;

    public SatelliteConstellation(String constellationName) {
        this.constellationName = constellationName;
        this.satellites = new ArrayList<>();
        System.out.printf("Создана спутниковая группировка: %s\n" +
                "---------------------------------------------\n", constellationName);
    }

    public void addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
        System.out.printf("%s добавлен в группировку '%s'\n", satellite.name, constellationName);
    }

    public void executeAllMissions() {
        for (Satellite sat : satellites) {
            sat.performMission();
        }
    }

    public List<Satellite> getSatellites() {
        return satellites;
    }
}
