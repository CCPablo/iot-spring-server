package internal.mqtt;

import internal.mqtt.listener.exception.ComponentNotRegisteredException;
import internal.mqtt.listener.exception.InvalidSensorValueException;
import internal.mqtt.listener.mapper.JsonMapper;
import internal.mqtt.listener.mapper.ProcessorMapper;
import internal.mqtt.persistence.PersistenceClient;
import internal.mqtt.publisher.ActuatorValueMsg;
import internal.mqtt.publisher.GatewayConnectMsg;
import internal.mqtt.topic.TopicsToPub;
import internal.mqtt.topic.TopicsToSub;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.stream;

@Component
@Slf4j
public class GatewayClient implements MqttCallbackExtended {

    @Value("${mqtt.server.uri:tcp://localhost:1883}")
    private String serverUri;

    @Value("${mqtt.client.id:gateway}")
    private String clientId;

    @Value("${mqtt.client.timeout:2}")
    private Integer timeout;

    @Value("${mqtt.client.showOnConnect:false}")
    private boolean showOnConnect;

    static private MqttAsyncClient client;

    private final ProcessorMapper processorMapper;

    private final PersistenceClient persistenceClient;

    public GatewayClient(ProcessorMapper processorMapper, PersistenceClient persistenceClient) {
        this.processorMapper = processorMapper;
        this.persistenceClient = persistenceClient;
    }

    @PostConstruct
    private void init() throws MqttException {
        client = new MqttAsyncClient(serverUri, clientId, persistenceClient);
        client.setCallback(this);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(timeout);
        client.connect(options);
    }

    @SneakyThrows
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("MQTT client connected");

        client.subscribe(
                stream(TopicsToSub.values()).map(TopicsToSub::getTopic).toArray(String[]::new),
                stream(TopicsToSub.values()).map(TopicsToSub::getQoS).mapToInt(i -> i).toArray());

        if (showOnConnect) {
            publishConnectionMessage(true);
            showOnConnect = false;
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.info("MQTT connection lost. Cause: " + cause.getMessage());

        try {
            client.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info(String.format("MQTT message arrived with topic: %s,and message: %s", topic, Arrays.toString(message.getPayload())));
        try {
            processorMapper.getMsgProcessor(topic).process(message);
        } catch (ComponentNotRegisteredException e) {
            log.error("No such component exception");
        } catch (InvalidSensorValueException e) {
            log.error("Invalid Sensor Value");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("MQTT message sent");
    }


    public static void publishConnectionMessage(Boolean connected) {
        publishMessage(new GatewayConnectMsg(connected), TopicsToPub.GATEWAY_CONNECT_TOPIC);
    }

    public static void publishUnitValueMessage(Integer nodeId, Integer unitId, Long value) {
        publishMessage(new ActuatorValueMsg(nodeId, unitId, value), TopicsToPub.UNIT_VALUE_TOPIC);
    }

    public static <T> void publishMessage(T t, TopicsToPub topicsToPub) {
        MqttMessage message = new MqttMessage();
        message.setPayload(Objects.requireNonNull(JsonMapper.getSerializedObject(t)).getBytes(StandardCharsets.UTF_8));

        try {
            log.info(String.format("Publishing message: in topic %s", topicsToPub.getTopic()));
            client.publish(topicsToPub.getTopic(), message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
