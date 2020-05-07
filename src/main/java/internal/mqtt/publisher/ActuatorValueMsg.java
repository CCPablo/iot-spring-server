package internal.mqtt.publisher;

import lombok.Data;

@Data
public class ActuatorValueMsg {

    private Integer nodeId;

    private Integer unitId;

    private Long value;
}
