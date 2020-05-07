package internal.scheduler.condition;

import internal.model.StatusType;
import internal.mqtt.service.DeviceService;

public class UnitCondition implements ICondition{

    private DeviceService deviceService;

    private Integer deviceId;

    private Integer actuatorId;

    private StatusType statusType;

    public UnitCondition(DeviceService deviceService, Integer deviceId, Integer actuatorId, StatusType statusType) {
        this.deviceService = deviceService;
        this.deviceId = deviceId;
        this.actuatorId = actuatorId;
        this.statusType = statusType;
    }

    @Override
    public boolean test() {
        return false;
    }
}
