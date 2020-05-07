package internal.model.unit;

import lombok.Data;

@Data
public class UnitValue {

    private Integer deviceId;

    private Integer unitId;

    private Long value;

    private Long timestamp;

    public UnitValue (Integer deviceId, Integer unitId, Long value) {
        this.deviceId = deviceId;
        this.unitId = unitId;
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }
}
