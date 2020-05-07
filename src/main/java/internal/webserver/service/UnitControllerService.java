package internal.webserver.service;

import internal.model.unit.UnitValue;
import internal.mqtt.GatewayClient;
import internal.mqtt.topic.TopicsToPub;
import org.springframework.stereotype.Service;

@Service
public class UnitControllerService {

    public void updateActuatorValue(Integer deviceId, Integer unitId, Long value) {
        GatewayClient.publishMessage(new UnitValue(deviceId, unitId, value), TopicsToPub.ACTUATOR_UPDATE_TOPIC);
    }
}
