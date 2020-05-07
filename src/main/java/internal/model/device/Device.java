package internal.model.device;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import internal.model.StatusType;
import internal.model.unit.Unit;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = Device.DeviceBuilder.class)
@Value
@RequiredArgsConstructor
@Builder
public class Device {

    Integer id;

    String name;

    String description;

    String connectedSince;

    String ip;

    StatusType status;

    List<Unit> units;

    @JsonPOJOBuilder(withPrefix = "")
        public static class DeviceBuilder {
    }
}