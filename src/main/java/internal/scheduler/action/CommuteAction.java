package internal.scheduler.action;

import internal.model.StatusType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommuteAction implements IAction {

    private Integer nodeId;

    private Integer actuatorId;

    private StatusType actuatorStatus;

    public CommuteAction(Integer nodeId, Integer actuatorId, StatusType actuatorStatus) {
        this.nodeId = nodeId;
        this.actuatorId = actuatorId;
        this.actuatorStatus = actuatorStatus;
    }

    @Override
    public void run() {
        log.info("Commutation Task Executed");
    }
}
