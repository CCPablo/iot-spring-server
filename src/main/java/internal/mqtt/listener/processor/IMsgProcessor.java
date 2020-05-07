package internal.mqtt.listener.processor;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface IMsgProcessor {

    void process(MqttMessage message);
}
