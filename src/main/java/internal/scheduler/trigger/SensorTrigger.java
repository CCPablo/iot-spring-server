package internal.scheduler.trigger;

import internal.service.NodeService;
import internal.service.SensorService;
import internal.util.BeanUtil;

public class SensorTrigger extends ATrigger {

    private final SensorService sensorService;

    long above;

    long below;

    protected SensorTrigger(Integer nodeId, Integer unitId) {
        super(nodeId, unitId);
        this.sensorService = BeanUtil.getBean(SensorService.class);
    }

    @Override
    public boolean test() {
        long sensorValue = sensorService.getMeanValue(nodeId, unitId);
        return sensorValue > above && sensorValue < below;
    }
}
