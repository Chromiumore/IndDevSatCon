package me.chromiumore;

import java.util.List;

public class SatelliteConstellation {
    private String constellationName;
    private List<Satellite> satellites;

    public void addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
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
