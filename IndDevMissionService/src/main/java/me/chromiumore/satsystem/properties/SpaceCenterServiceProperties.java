package me.chromiumore.satsystem.properties;

import lombok.Data;
import me.chromiumore.satsystem.domain.mission.MissionTargetType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app.space-center-service")
public record SpaceCenterServiceProperties(String url, List<ConfiguredMission> missions) {
    @Data
    public static class ConfiguredMission {
        private MissionTargetType targetType;
        private String constellationName;
        private String satelliteName;
        private String cron;
    }
}
