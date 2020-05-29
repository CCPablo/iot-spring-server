package internal.mqtt.publisher;

import lombok.Data;

@Data
public class ActuatorValueMsg {

    private Integer nodeId;

    private Integer unitId;

    private Long value;

    public ActuatorValueMsg(Integer nodeId, Integer unitId, Long value) {
        this.nodeId = nodeId;
        this.unitId = unitId;
        this.value = value;
    }
}
