package internal.mqtt.listener.mapper;

import internal.mqtt.listener.exception.TopicNotRegisteredException;
import internal.mqtt.listener.processor.DeviceConnectionProcessor;
import internal.mqtt.listener.processor.DeviceInitProcessor;
import internal.mqtt.listener.processor.IMsgProcessor;
import internal.mqtt.listener.processor.UnitValueProcessor;
import internal.mqtt.topic.TopicsToSub;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProcessorMapper {

    private final DeviceInitProcessor deviceInitProcessor;

    private final UnitValueProcessor unitValueProcessor;

    public IMsgProcessor getMsgProcessor(String topic) throws TopicNotRegisteredException {
        if (TopicsToSub.UNIT_VALUE.topicMatch(topic)) {
            return unitValueProcessor;
        } else if (TopicsToSub.DEVICE_CONNECT_INIT.topicMatch(topic)) {
            return deviceInitProcessor;
        } else {
            throw new TopicNotRegisteredException();
        }
    }
}
