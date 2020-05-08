package internal.mqtt.listener.processor;

import internal.model.node.Node;
import internal.mqtt.listener.mapper.JsonMapper;
import internal.service.NodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class NodeConnectionProcessor implements IMsgProcessor {

    @Autowired
    NodeService nodeService;

    @Override
    public void process(MqttMessage message) {
        Node nodeConnectionMsg = JsonMapper.getDeserializedMessage(message, Node.class);
    }
}
