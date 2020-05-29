package internal.scheduler.action;

import internal.mqtt.GatewayClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetValueAction implements IAction {

    private final Integer nodeId;

    private final Integer actuatorId;

    private final Long value;

    public SetValueAction(Integer nodeId, Integer actuatorId, Long value) {
        this.nodeId = nodeId;
        this.actuatorId = actuatorId;
        this.value = value;
    }

    @Override
    public void run() {
        log.info("Set Value task Executed");
        GatewayClient.publishUnitValueMessage(nodeId, actuatorId, value);
    }
}
