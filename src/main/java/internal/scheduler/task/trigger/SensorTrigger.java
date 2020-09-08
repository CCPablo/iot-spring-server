package internal.scheduler.task.trigger;

import internal.service.SensorService;
import internal.util.BeanUtil;

public class SensorTrigger extends ATrigger {

    private final SensorService sensorService;

    private final Long highLimit;

    private final Long lowLimit;

    public SensorTrigger(Integer nodeId, Integer unitId, Long highLimit, Long lowLimit) {
        super(nodeId, unitId);
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.sensorService = BeanUtil.getBean(SensorService.class);
    }

    public SensorTrigger(Integer nodeId, Integer unitId, Long highLimit) {
        super(nodeId, unitId);
        this.highLimit = highLimit;
        this.lowLimit = -1L;
        this.sensorService = BeanUtil.getBean(SensorService.class);
    }

    @Override
    public boolean test() {
        if (sensorValueIsOutOfRange()) {
            triggeredFlag = false;
        } else {
            if(triggeredFlag) {
                return false;
            } else {
                triggeredFlag = true;
                return true;
            }
        }
        return false;
    }

    private boolean sensorValueIsOutOfRange() {
        long sensorValue = sensorService.getMeanValue(nodeId, unitId);
        return (highLimit == -1 || sensorValue <= highLimit) && (lowLimit == -1 || sensorValue >= lowLimit);
    }
}
