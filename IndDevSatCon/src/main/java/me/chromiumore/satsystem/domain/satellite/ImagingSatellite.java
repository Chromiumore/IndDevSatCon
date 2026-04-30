package me.chromiumore.satsystem.domain.satellite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("IMAGING")
@NoArgsConstructor
public class ImagingSatellite extends Satellite {
    @Getter @Setter
    private double resolution;
    @Getter
    private int photosTaken;

    public ImagingSatellite(String name, double batteryLevel, double resolution) {
        super(name, batteryLevel);
        this.resolution = resolution;
        photosTaken = 0;
    }

    @Override
    public void performMission() {
        if (state.isActive()) {
            energy.consume(0.08);
            takePhoto();
            return;
        }

        System.out.printf("\uD83D\uDED1 %s: Не может выполнить съемку - не активен\n", name);
    }

    private void takePhoto() {
        System.out.printf("%s: Съемка территории с разрешением %.1f м/пиксель\n", name, resolution);
        photosTaken++;
        System.out.printf("%s: Снимок #%d сделан!\n", name, photosTaken);
    }

    @Override
    public String toString() {
        return String.format("ImagingSatellite{resolution=%.1f, photosTaken=%d, name='%s', isActive=%b, batteryLevel=%.2f}", resolution, photosTaken, name, state.isActive(), energy.getBatteryLevel());
    }
}
