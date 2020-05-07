package internal.scheduler.task;

import internal.mqtt.GatewayClient;
import internal.model.StatusType;
import internal.mqtt.publisher.ActuatorValueMsg;
import internal.mqtt.topic.TopicsToPub;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommuteTask implements ITask {

    private Integer deviceId;

    private Integer actuatorId;

    private StatusType actuatorStatus;

    public CommuteTask(Integer deviceId, Integer actuatorId, StatusType actuatorStatus) {
        this.deviceId = deviceId;
        this.actuatorId = actuatorId;
        this.actuatorStatus = actuatorStatus;
    }

    public String toString() {
        return "Task commutated";
    }

    @Override
    public void run() {
    }
}
