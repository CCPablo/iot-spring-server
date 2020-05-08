package internal.scheduler.trigger;

public class SensorTrigger extends ITrigger {

    long above;

    long below;

    protected SensorTrigger(Integer nodeId, Integer unitId) {
        super(nodeId, unitId);
    }

    @Override
    public boolean test() {
        return false;
    }
}
