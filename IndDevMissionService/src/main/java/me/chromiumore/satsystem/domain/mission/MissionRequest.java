package me.chromiumore.satsystem.domain.mission;

public record MissionRequest(MissionTargetType targetType, String constellationName, String satelliteName) {
}
