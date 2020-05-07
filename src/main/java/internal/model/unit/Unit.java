package internal.model.unit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import internal.model.StatusType;
import internal.model.device.Device;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@JsonDeserialize(builder = Unit.UnitBuilder.class)
@Value
@RequiredArgsConstructor
@Builder
public class Unit {

    Integer deviceId;

    Integer id;

    String name;

    String description;

    UnitType type;

    Long measureRate;

    StatusType status;

    @JsonPOJOBuilder(withPrefix = "")
        public static class UnitBuilder {
    }
}
