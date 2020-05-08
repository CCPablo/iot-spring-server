package internal.scheduler.trigger;

import internal.service.SensorService;
import org.springframework.stereotype.Component;

@Component
public class SensorTrigger extends ATrigger {

    private final SensorService sensorService;

    long above;

    long below;

    protected SensorTrigger(Integer nodeId, Integer unitId, SensorService sensorService) {
        super(nodeId, unitId);
        this.sensorService = sensorService;
    }

    @Override
    public boolean test() {
        long sensorValue = sensorService.getMeanValue(nodeId, unitId);
        return sensorValue > above && sensorValue < below;
    }
}
