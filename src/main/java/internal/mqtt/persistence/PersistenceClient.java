package internal.mqtt.persistence;

import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.Vector;

@Component
public class PersistenceClient implements MqttClientPersistence {
    @Override
    public void open(String s, String s1) throws MqttPersistenceException {

    }

    @Override
    public void close() throws MqttPersistenceException {

    }

    @Override
    public void put(String s, MqttPersistable mqttPersistable) throws MqttPersistenceException {

    }

    @Override
    public MqttPersistable get(String s) throws MqttPersistenceException {
        return null;
    }

    @Override
    public void remove(String s) throws MqttPersistenceException {

    }

    @Override
    public Enumeration keys() throws MqttPersistenceException {
        return new Vector<String>().elements();
    }

    @Override
    public void clear() throws MqttPersistenceException {

    }

    @Override
    public boolean containsKey(String s) throws MqttPersistenceException {
        return false;
    }
}
