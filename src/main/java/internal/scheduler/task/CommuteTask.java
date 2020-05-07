package internal.scheduler.task;

import internal.mqtt.GatewayClient;
import internal.model.StatusType;
import internal.mqtt.publisher.ActuatorValueMsg;
import internal.mqtt.topic.TopicsToPub;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommuteTask implements ITask {

    private Integer nodeId;

    private Integer actuatorId;

    private StatusType actuatorStatus;

    public CommuteTask(Integer nodeId, Integer actuatorId, StatusType actuatorStatus) {
        this.nodeId = nodeId;
        this.actuatorId = actuatorId;
        this.actuatorStatus = actuatorStatus;
    }

    @Override
    public void run() {
        log.info("Commutation Task Executed");
    }
}
