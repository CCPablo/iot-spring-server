package internal.scheduler.condition;

import internal.model.StatusType;
import internal.mqtt.service.NodeService;

public class UnitCondition implements ICondition {

    private NodeService nodeService;

    private Integer nodeId;

    private Integer actuatorId;

    private StatusType statusType;

    public UnitCondition(NodeService nodeService, Integer nodeId, Integer actuatorId, StatusType statusType) {
        this.nodeService = nodeService;
        this.nodeId = nodeId;
        this.actuatorId = actuatorId;
        this.statusType = statusType;
    }

    @Override
    public boolean test() {
        return false;
    }
}
