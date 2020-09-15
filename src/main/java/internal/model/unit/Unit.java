package internal.model.unit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import internal.model.LocationType;
import internal.model.StatusType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@JsonDeserialize(builder = Unit.UnitBuilder.class)
@Value
@RequiredArgsConstructor
@Builder
public class Unit {

    Integer nodeId;

    Integer id;

    String name;

    String description;

    LocationType locationType;

    String locationName;

    UnitType type;

    String graphColor;

    Long measureRate;

    StatusType status;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UnitBuilder {
    }
}
