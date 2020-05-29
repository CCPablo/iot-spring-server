package internal.mqtt.topic;

public enum TopicsToPub {

    GATEWAY_CONNECT_TOPIC("/gateway/connect"),
    UNIT_VALUE_TOPIC("/gateway/unit/value");

    private final String topic;

    TopicsToPub(final String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
