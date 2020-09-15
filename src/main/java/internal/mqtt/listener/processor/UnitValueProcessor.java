package internal.mqtt.listener.processor;

import internal.model.unit.UnitMeasure;
import internal.mqtt.mapper.JsonMapper;
import internal.service.SensorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UnitValueProcessor implements IMsgProcessor {

    private final SensorService unitService;

    @Override
    public void process(MqttMessage message) {
        UnitMeasure actuatorUpdateMsg = JsonMapper.getDeserializedMessage(message, UnitMeasure.class);

        assert actuatorUpdateMsg != null;
        unitService.addValue(actuatorUpdateMsg);
    }
}
