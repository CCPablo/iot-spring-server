package internal.scheduler.trigger;

import internal.mqtt.service.NodeService;

public abstract class ITrigger {

    private final Integer nodeId;

    private final Integer unitId;

    protected ITrigger(Integer nodeId, Integer unitId) {
        if (!NodeService.isUnitPresent(nodeId, unitId)) {
            throw new ExceptionInInitializerError();
        }
        this.nodeId = nodeId;
        this.unitId = unitId;
    }

    public boolean unitToTrigger(Integer nodeId, Integer unitId) {
        return nodeId.equals(this.nodeId) && unitId.equals(this.unitId);
    }

    public boolean test() {
        return false;
    }
}
