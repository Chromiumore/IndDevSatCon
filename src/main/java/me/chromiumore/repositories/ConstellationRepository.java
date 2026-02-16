package me.chromiumore.repositories;

import me.chromiumore.SatelliteConstellation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConstellationRepository {
    private Map<String, SatelliteConstellation> constellations = new HashMap<>();;

    public void add(SatelliteConstellation constellation) {
        constellations.put(constellation.getConstellationName(), constellation);
        System.out.println("Сохранена группировка: " + constellation.getConstellationName());
    }

    public SatelliteConstellation get(String name) {
        SatelliteConstellation constellation = constellations.get(name);
        if (constellation == null) {
            throw new RuntimeException("Группировка не найдена: " + name);
        }
        return constellation;
    }

    public Map<String, SatelliteConstellation> getAll() {
        return constellations;
    }

    public boolean contains(String name) {
        return constellations.containsKey(name);
    }

    public void update(String name, SatelliteConstellation constellation) {
        if (!constellations.containsKey(name)) {
            throw new RuntimeException("Невозможно обновить группировку. Не найдено: : " + name);
        }
        constellations.replace(name, constellation);
    }

    public void remove(String name) {
        constellations.remove(name);
        System.out.printf("Группировка удалена: " + name);
    }
}
