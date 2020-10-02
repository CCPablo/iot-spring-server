package internal.scheduler.task.action;

import internal.mqtt.GatewayClient;
import internal.util.key.KeyFormatter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SetValueAction implements Runnable {

    private final Integer nodeId;

    private final Integer actuatorId;

    private final Long value;

    private final Long millisDelay;

    private final static ActionType actionType = ActionType.SET_VALUE;

    private final static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public SetValueAction(Integer nodeId, Integer actuatorId, Long value) {
        this.nodeId = nodeId;
        this.actuatorId = actuatorId;
        this.value = value;
        this.millisDelay = 0L;
    }
    public SetValueAction(Integer nodeId, Integer actuatorId, Long value, Long millisDelay) {
        this.nodeId = nodeId;
        this.actuatorId = actuatorId;
        this.value = value;
        this.millisDelay = millisDelay;
    }

    @Override
    public void run() {
        executorService.schedule(runnable, millisDelay, TimeUnit.MILLISECONDS);
        GatewayClient.publishUnitValueMessage(nodeId, actuatorId, value);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            log.info(String.format("Set value task executed for actuator %s with value %d", KeyFormatter.getUnitKey(nodeId, actuatorId), value));
            GatewayClient.publishUnitValueMessage(nodeId, actuatorId, value);
        }
    };
}
