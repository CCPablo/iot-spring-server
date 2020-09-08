package internal.scheduler.task.trigger;

import internal.service.NodeService;
import internal.util.BeanUtil;

public abstract class ATrigger {

    protected final Integer nodeId;

    protected final Integer unitId;

    protected boolean triggeredFlag = false;

    protected ATrigger(Integer nodeId, Integer unitId) {
        NodeService nodeService = BeanUtil.getBean(NodeService.class);
        if (!nodeService.isUnitPresent(nodeId, unitId)) {
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
