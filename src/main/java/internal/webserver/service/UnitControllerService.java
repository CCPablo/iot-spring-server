package internal.webserver.service;

import internal.model.unit.UnitValue;
import internal.mqtt.GatewayClient;
import internal.mqtt.topic.TopicsToPub;
import org.springframework.stereotype.Service;

@Service
public class UnitControllerService {

    public void updateActuatorValue(Integer nodeId, Integer unitId, Long value) {
        GatewayClient.publishMessage(UnitValue.builder().nodeId(nodeId).unitId(unitId).value(value).build(), TopicsToPub.UNIT_VALUE_TOPIC);
    }
}
