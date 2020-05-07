package internal.mqtt.publisher;

import lombok.Data;

@Data
public class ActuatorValueMsg {

    private Integer deviceId;

    private Integer unitId;

    private Long value;
}
