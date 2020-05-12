package internal.scheduler.trigger;

import internal.service.SensorService;
import internal.util.BeanUtil;

public class SensorTrigger extends ATrigger {

    private final SensorService sensorService;

    private final Long above;

    private final Long below;

    protected SensorTrigger(Integer nodeId, Integer unitId, Long above, Long below) {
        super(nodeId, unitId);
        this.above = above;
        this.below = below;
        this.sensorService = BeanUtil.getBean(SensorService.class);
    }

    protected SensorTrigger(Integer nodeId, Integer unitId, Long above) {
        super(nodeId, unitId);
        this.above = above;
        this.below = -1L;
        this.sensorService = BeanUtil.getBean(SensorService.class);
    }

    @Override
    public boolean test() {
        long sensorValue = sensorService.getMeanValue(nodeId, unitId);
        return above != -1 ? sensorValue > above : below == -1 || sensorValue < below;
    }
}
