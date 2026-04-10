package me.chromiumore.satsystem.domain.satellite.param;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.chromiumore.satsystem.domain.satellite.param.impl.CommunicationSatelliteParam;
import me.chromiumore.satsystem.domain.satellite.param.impl.ImagingSatelliteParam;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CommunicationSatelliteParam.class, name = "COMMUNICATION"),
        @JsonSubTypes.Type(value = ImagingSatelliteParam.class, name = "IMAGE")
})
@Getter @Setter
@AllArgsConstructor
public abstract class SatelliteParam {
    protected SatelliteType type;
    protected String name;
    protected double batteryLevel;
}
