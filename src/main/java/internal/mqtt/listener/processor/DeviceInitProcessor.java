package internal.mqtt.listener.processor;

import internal.model.device.Device;
import internal.mqtt.listener.mapper.JsonMapper;
import internal.mqtt.service.DeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class DeviceInitProcessor implements IMsgProcessor {

    @Autowired
    DeviceService deviceService;

    @Override
    public void process(MqttMessage message) {
        Device deviceInitMsg = JsonMapper.getDeserializedMessage(message, Device.class);

        deviceService.addNewDevice(deviceInitMsg);
    }
}
