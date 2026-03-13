package me.chromiumore.satsystem.domain.request;

public record MissionRequest(MissionTargetType targetType, String constellationName, String satelliteName) {
}
