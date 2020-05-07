package internal.model.unit;

import lombok.Data;

@Data
public class UnitValue {

    private Integer nodeId;

    private Integer unitId;

    private Long value;

    private Long timestamp;

    public UnitValue(Integer nodeId, Integer unitId, Long value) {
        this.nodeId = nodeId;
        this.unitId = unitId;
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }
}
